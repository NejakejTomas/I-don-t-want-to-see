package cz.kiec.idontwanttosee.ui.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable

interface ScreenControllable {
    @Composable
    fun Title(value: String)

    @Composable
    fun Actions(value: @Composable RowScope.() -> Unit)
}