package cz.kiec.idontwanttosee.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import cz.kiec.idontwanttosee.ui.navigation.AddRule
import cz.kiec.idontwanttosee.ui.navigation.Main
import cz.kiec.idontwanttosee.ui.navigation.ModifyRule
import cz.kiec.idontwanttosee.ui.navigation.Rules
import cz.kiec.idontwanttosee.ui.screen.AddRuleScreen
import cz.kiec.idontwanttosee.ui.screen.MainScreen
import cz.kiec.idontwanttosee.ui.screen.ModifyRuleScreen
import cz.kiec.idontwanttosee.ui.screen.RulesScreen
import cz.kiec.idontwanttosee.ui.screen.ScreenDecors

@Composable
fun Application(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    var screenDecors by remember {
        mutableStateOf(ScreenDecors())
    }

    Scaffold(
        modifier = modifier,

        topBar = {
            AppBar(
                title = screenDecors.title.orEmpty(),
                canGoBack = navController.previousBackStackEntry != null,
                goBack = { navController.navigateUp() },
                actions = screenDecors.actions
            )
        }

    ) { contentPadding ->
        NavHost(
            modifier = Modifier
                .padding(contentPadding)
                .padding(Dimens.D7, 0.dp, Dimens.D7, Dimens.D7),
            navController = navController,
            startDestination = Main,
        ) {
            composable<Main> {
                MainScreen(
                    setScreenDecors = { screenDecors = it(ScreenDecors()) },
                    navController = navController,
                )
            }

            composable<Rules> {
                RulesScreen(
                    setScreenDecors = { screenDecors = it(ScreenDecors()) },
                    navController = navController,
                )
            }

            composable<AddRule> {
                AddRuleScreen(
                    setScreenDecors = { screenDecors = it(ScreenDecors()) },
                    navController = navController,
                )
            }

            composable<ModifyRule> {
                val route: ModifyRule = it.toRoute()

                ModifyRuleScreen(
                    id = route.id,
                    setScreenDecors = { screenDecors = it(ScreenDecors()) },
                    navController = navController,
                )
            }
        }
    }
}