package cz.kiec.idontwanttosee.ui.screen

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import cz.kiec.idontwanttosee.ui.elements.BottomNavigationBarEntry

data class ScreenDecors(
    val title: String? = null,
    val topBarActions: (@Composable RowScope.() -> Unit)? = null,
    val bottomNavigationItems: List<BottomNavigationBarEntry> = listOf(),
)