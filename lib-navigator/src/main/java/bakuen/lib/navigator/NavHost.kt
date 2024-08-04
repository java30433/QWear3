package bakuen.lib.navigator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocal
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.saveable.SaveableStateHolder
import androidx.compose.runtime.saveable.rememberSaveableStateHolder

fun compositionLocalOfNavigator() = compositionLocalOf<Navigator?> { null }
val LocalNavigator = compositionLocalOfNavigator()

val <T> CompositionLocal<T?>.currentOrThrow
    @Composable get() = current ?: throw NullPointerException()

private var mStateHolder: SaveableStateHolder? = null
internal val stateHolder get() = mStateHolder!!
@Composable
fun NavHost(
    initialScreen: Screen,
    content: @Composable (@Composable ()->Unit)->Unit
) {
    if(mStateHolder == null) mStateHolder = rememberSaveableStateHolder()
    val navigator = Navigator(initialScreen)
    CompositionLocalProvider(LocalNavigator provides navigator) {
        content { CurrentScreen() }
    }
}