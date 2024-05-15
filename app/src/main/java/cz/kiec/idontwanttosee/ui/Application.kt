package cz.kiec.idontwanttosee.ui

import androidx.compose.foundation.layout.RowScope
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
import cz.kiec.idontwanttosee.ui.navigation.ScreenControllable
import cz.kiec.idontwanttosee.ui.screen.AddRuleScreen
import cz.kiec.idontwanttosee.ui.screen.MainScreen
import cz.kiec.idontwanttosee.ui.screen.ModifyRuleScreen
import cz.kiec.idontwanttosee.ui.screen.RulesScreen

@Composable
fun Application(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    var title by remember {
        mutableStateOf("")
    }

    var actions by remember {
        mutableStateOf<@Composable (RowScope.() -> Unit)>({})
    }

    class ScreenControllableImpl : ScreenControllable {
        init {
            title = ""
            actions = {}
        }

        @Composable
        override fun Title(value: String) {
            title = value
        }

        @Composable
        override fun Actions(value: @Composable (RowScope.() -> Unit)) {
            actions = value
        }
    }

    Scaffold(
        modifier = modifier,

        topBar = {
            AppBar(
                title,
                navController.previousBackStackEntry != null,
                { navController.navigateUp() },
                actions
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
                MainScreen(ScreenControllableImpl(), navController = navController)
            }

            composable<Rules> {
                RulesScreen(ScreenControllableImpl(), navController = navController)
            }

            composable<AddRule> {
                AddRuleScreen(ScreenControllableImpl(), navController = navController)
            }

            composable<ModifyRule> {
                val rule: ModifyRule = it.toRoute()
                ModifyRuleScreen(
                    ScreenControllableImpl(),
                    navController = navController,
                    id = rule.id
                )
            }
        }
    }
}