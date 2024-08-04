package bakuen.lib.navigator

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.saveable.rememberSaveableStateHolder

@Composable
internal fun CurrentScreen(
    enter: EnterTransition = slideInHorizontally(),
    exit: ExitTransition = slideOutHorizontally()
) {
    val navigator = LocalNavigator.currentOrThrow
    navigator.screenStack.forEach {
        stateHolder.SaveableStateProvider(key = it.hashCode()) {
            AnimatedVisibility(
                visible = it == navigator.currentScreen,
                enter = enter,
                exit = exit
            ) {
                it()
            }
        }
    }
}