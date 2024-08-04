@file:Suppress("UNCHECKED_CAST")
@file:OptIn(ExperimentalSerializationApi::class)

package bakuen.lib.protostore

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalInspectionMode
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.protobuf.ProtoBuf
import java.io.File

val protobuf = ProtoBuf { }
class SerializerCache<T: ProtoStore>(val serializer: KSerializer<T>, val name: String, var store: T? = null)
val serializerCache = mutableMapOf<Class<*>, SerializerCache<*>>()
interface ProtoStore

inline fun <reified T: ProtoStore> getSerializer(): SerializerCache<T> {
    return serializerCache.getOrPut(T::class.java) {
        val companion = T::class.java.getDeclaredField("Companion").get(null)
        val serializer = companion.javaClass.getDeclaredMethod("serializer").invoke(companion) as KSerializer<T>
        SerializerCache(serializer, serializer.descriptor.serialName)
    } as SerializerCache<T>
}
inline fun <reified T: ProtoStore> getStore(): T {
    val cache = getSerializer<T>()
    if (cache.store == null) {
        cache.store = protobuf.decodeFromByteArray(cache.serializer, appContext.getFile(cache.name).readBytes()) as T
    }
    return cache.store as T
}

inline fun <reified T: ProtoStore> writeStore(value: T) {
    val cache = getSerializer<T>()
    cache.store = value
    appContext.getFile(cache.name).writeBytes(protobuf.encodeToByteArray(cache.serializer, value))
}
inline fun <reified T: ProtoStore> setStore(block: (T)->T) {
    writeStore(block(getStore<T>()))
}

@Composable
inline fun <reified T: ProtoStore> rememberStore(preview: T? = null): MutableState<T> {
    if (preview != null && LocalInspectionMode.current) return remember { mutableStateOf(preview) }
    val cache = getSerializer<T>()
    val state = remember { mutableStateOf(getStore<T>()) }
    LaunchedEffect(state.value == cache.store) {
        writeStore(state.value)
    }
    return state
}

fun Context.getFile(name: String): File {
    val f = File(filesDir, name)
    if (!f.exists()) f.createNewFile()
    return f
}