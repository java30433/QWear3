package bakuen.qwear.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Modifier.condition(condition: Boolean, modifier: Modifier) =
    if (condition) then(modifier) else this