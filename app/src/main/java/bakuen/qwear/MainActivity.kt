package bakuen.qwear

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import bakuen.lib.navigator.LocalNavigator
import bakuen.lib.navigator.NavHost
import bakuen.lib.navigator.currentOrThrow
import bakuen.lib.protostore.rememberStore
import bakuen.qwear.prefs.AccountData
import bakuen.qwear.screens.main.MainScreen
import bakuen.qwear.utils.Background
import bakuen.qwear.utils.QWearTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QWearTheme(designWidth = 360f) {
                Background(color = Color.Black, fillMaxSize = true) {
                    NavHost(initialScreen = { MainScreen() }) { currentScreen ->
                        Background(color = Color.Black, contentAlignment = Alignment.Center, fillMaxSize = true) {
                            currentScreen()
                        }
                    }
                }
            }
        }
    }
}