package bakuen.qwear.utils

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

class TextState(default: String, private val filter: ((String) -> String)? = null) {
    var text by mutableStateOf(default)
    fun tryChangeText(value: String) {
        text = filter?.invoke(value) ?: value
    }
}

@Composable
fun rememberTextState(default: String = "", filter: ((String) -> String)? = null) =
    remember { TextState(default, filter) }

@Composable
fun OutlinedTextField(
    modifier: Modifier = Modifier,
    state: TextState,
    hint: String? = null,
    singleLine: Boolean = true,
) {
    androidx.compose.material3.OutlinedTextField(
        modifier = Modifier,
        value = state.text,
        onValueChange = { state.tryChangeText(it) },
        placeholder = hint?.let { { Text(text = it) } },
        singleLine = singleLine
    )
}