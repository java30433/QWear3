package bakuen.qwear.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density

@Composable
fun QWearTheme(
    designWidth: Float,
    content: @Composable () -> Unit,
) {
    val colorScheme = darkColorScheme()
    val fontScale = LocalDensity.current.fontScale
    val displayMetrics = LocalContext.current.resources.displayMetrics
    val widthPixels = displayMetrics.widthPixels
    CompositionLocalProvider(
        LocalDensity provides Density(
            density = widthPixels / designWidth,
            fontScale = fontScale
        )
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}