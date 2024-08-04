package bakuen.lib.navigator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

typealias Screen = @Composable ()->Unit

class Navigator(val initialScreen: Screen) {
    internal var screenStack by mutableStateOf(listOf(initialScreen))
    fun push(screen: Screen) {
        screenStack = screenStack.plus(screen)
    }
    fun pop(): Boolean {
        if (screenStack.size == 1) return false
        screenStack = screenStack.dropLast(1)
        return true
    }
    fun size() = screenStack.size
    fun replaceAll(screen: Screen) {
        screenStack = listOf(screen)
    }
    fun replaceTop(screen: Screen) {
        screenStack = screenStack.dropLast(1).plus(screen)
    }
    val currentScreen get() = screenStack.last()
}