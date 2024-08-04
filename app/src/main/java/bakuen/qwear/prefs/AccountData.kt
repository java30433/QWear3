package bakuen.qwear.prefs

import bakuen.lib.protostore.ProtoStore
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class AccountData(
    /**
     * 服务器地址，如 https://[0this.is.ipv6.address]:port/
     */
    @ProtoNumber(1)
    val serverBase: String,
    @ProtoNumber(2)
    val token: String
) : ProtoStore