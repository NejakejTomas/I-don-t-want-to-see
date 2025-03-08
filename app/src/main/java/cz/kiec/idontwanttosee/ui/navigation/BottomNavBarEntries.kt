package cz.kiec.idontwanttosee.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import cz.kiec.idontwanttosee.R
import cz.kiec.idontwanttosee.ui.elements.BottomNavigationBarEntry

object BottomNavBarEntries {
    val global
        @Composable
        get() = listOf(
            BottomNavigationBarEntry(
                stringResource(R.string.navigation_bar_item_rules),
                { Icon(Icons.AutoMirrored.Filled.List, null) },
                Screen.Rules
            ),
            BottomNavigationBarEntry(
                stringResource(R.string.navigation_bar_item_channels),
                { Icon(Icons.Filled.Notifications, null) },
                Screen.NotificationChannels
            )
        )
}