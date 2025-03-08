package cz.kiec.idontwanttosee.ui.elements

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import cz.kiec.idontwanttosee.ui.navigation.Screen
import kotlinx.coroutines.flow.map

data class BottomNavigationBarEntry(
    val label: String,
    val icon: @Composable () -> Unit,
    val screen: Screen,
)

@Composable
fun BottomNavigationBar(
    navController: NavController,
    entries: List<BottomNavigationBarEntry>,
    modifier: Modifier = Modifier,
) {
    val currentScreen by navController.currentBackStackEntryFlow
        .map { it.destination }
        .collectAsStateWithLifecycle(
            initialValue = null
        )

    val shouldShow = entries.isNotEmpty()
    if (!shouldShow) return

    NavigationBar(modifier) {
        entries.forEach { entry ->
            NavigationBarItem(
                selected = currentScreen?.hasRoute(entry.screen::class) == true,
                icon = entry.icon,
                onClick = {
                    navController.navigate(entry.screen) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                label = { Text(entry.label) },
                alwaysShowLabel = true,
            )
        }
    }
}