package cz.kiec.idontwanttosee.ui.screen

import androidx.compose.runtime.Composable

data class BottomNavigationBarEntry(
    val label: String,
    val icon: @Composable () -> Unit,
    val screen: Any,
)

data class ClickableIcon(
    val icon: @Composable () -> Unit,
    val onClick: () -> Unit,
)

data class ScreenDecors(
    val title: String? = null,
    val topBarActions: List<ClickableIcon> = listOf(),
    val bottomNavigationEntries: List<BottomNavigationBarEntry> = listOf(),
    val floatingButton: ClickableIcon? = null,
)