package bakuen.qwear.api

import android.net.Uri
import bakuen.lib.protostore.getStore
import bakuen.qwear.prefs.AccountData
import io.grpc.ManagedChannelBuilder
import io.kritor.core.CoreServiceGrpcKt
import io.kritor.recent.RecentServiceGrpcKt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor

object KritorClient {
    private val accountData = getStore<AccountData>()

    private val channel = let {
        val uri = Uri.parse(accountData.serverBase)
        val builder = ManagedChannelBuilder.forAddress(uri.host, uri.port)
        if (uri.scheme == "https") {
            builder.useTransportSecurity()
        } else {
            builder.usePlaintext()
        }
        builder.executor(Dispatchers.IO.asExecutor())
        builder.enableRetry()
    }.build()

    val recent by lazy { RecentServiceGrpcKt.RecentServiceCoroutineStub(channel) }
    val core by lazy { CoreServiceGrpcKt.CoreServiceCoroutineStub(channel) }
}