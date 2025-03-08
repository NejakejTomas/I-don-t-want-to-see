package cz.kiec.idontwanttosee.ui.elements

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import cz.kiec.idontwanttosee.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    title: String?,
    navController: NavController,
    modifier: Modifier = Modifier,
    actions: (@Composable RowScope.() -> Unit)? = null,
    bottomNavBarEntries: List<BottomNavigationBarEntry> = listOf(),
) {
    val isOnTop = bottomNavBarEntries.any {
        navController.currentDestination?.hasRoute(it.screen::class) == true
    }

    val canGoBack = navController.previousBackStackEntry != null && !isOnTop

    val shouldShow = title != null || canGoBack || actions != null
    if (!shouldShow) return


    CenterAlignedTopAppBar(
        title = {
            title?.let {
                Text(it)
            }
        },
        modifier = modifier,
        navigationIcon = icon@{
            if (!canGoBack) return@icon

            IconButton(onClick = navController::navigateUp) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.top_bar_back_button_description)
                )
            }
        },
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
        actions = actions ?: { },
    )
}