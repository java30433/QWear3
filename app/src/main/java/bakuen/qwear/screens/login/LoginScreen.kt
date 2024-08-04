package bakuen.qwear.screens.login

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bakuen.lib.protostore.getStore
import bakuen.lib.protostore.setStore
import bakuen.qwear.prefs.AccountData
import bakuen.qwear.utils.OutlinedTextField
import bakuen.qwear.utils.rememberTextState

private class State()
private sealed class Event {
    class TryLogin(val address: String) : Event()
}

@Composable
fun LoginScreen() {
    LoginScreenUI(dispatch = { event ->
        when (event) {
            is Event.TryLogin -> {
                setStore<AccountData> {
                    it.copy(serverBase = event.address)
                }
            }
        }
    })
}

@Composable
private fun LoginScreenUI(dispatch: (Event)->Unit) {
    val ipFieldState = rememberTextState()
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(text = "连接手机QQ服务", style = MaterialTheme.typography.titleMedium)
        OutlinedTextField(state = ipFieldState, hint = "服务器地址")
        FilledTonalButton(onClick = { dispatch(Event.TryLogin(ipFieldState.text)) }) {
            Text(text = "登录")
        }
    }
}