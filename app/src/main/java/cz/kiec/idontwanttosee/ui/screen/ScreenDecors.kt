package cz.kiec.idontwanttosee.ui.screen

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable

data class ScreenDecors(
    val title: String? = null,
    val actions: (@Composable RowScope.() -> Unit)? = null,
)