package bakuen.qwear.screens.main

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import bakuen.lib.navigator.LocalNavigator
import bakuen.lib.navigator.currentOrThrow
import bakuen.lib.protostore.rememberStore
import bakuen.qwear.api.KritorClient
import bakuen.qwear.prefs.AccountData
import bakuen.qwear.screens.login.LoginScreen
import io.kritor.core.GetCurrentAccountRequest
import io.kritor.recent.GetRecentContactInfosRequest

private const val TRY_LOGIN = 0
private const val LOGIN_FAILED = 1
private const val LOGIN_SUCCESS = 2

private data class State(
    val loginStatus: Int = TRY_LOGIN,
    val qqNumber: String = "",
    val nickName: String = ""
)

@Composable
fun MainScreen() {
    var state by remember { mutableStateOf(State()) }
    LaunchedEffect(Unit) {
        val currentAccount =
            KritorClient.core.getCurrentAccount(GetCurrentAccountRequest.getDefaultInstance())
        state = if (currentAccount.accountUid.isEmpty()) state.copy(loginStatus = LOGIN_FAILED)
        else state.copy(
            loginStatus = LOGIN_SUCCESS,
            qqNumber = currentAccount.accountUid,
            nickName = currentAccount.accountName
        )
    }
    MainScreenUI(state = state)
}

@Composable
private fun MainScreenUI(state: State) {
    when (state.loginStatus) {
        TRY_LOGIN -> {
            CircularProgressIndicator()
        }
        LOGIN_FAILED -> {
            LocalNavigator.currentOrThrow.replaceAll { LoginScreen() }
        }
        LOGIN_SUCCESS -> {
            Text(text = "${state.nickName} ${state.qqNumber}")
        }
    }
}