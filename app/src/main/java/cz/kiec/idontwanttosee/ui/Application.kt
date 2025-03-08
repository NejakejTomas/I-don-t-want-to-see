package cz.kiec.idontwanttosee.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import cz.kiec.idontwanttosee.ui.navigation.Screen
import cz.kiec.idontwanttosee.ui.screen.addnotificationchannel.AddNotificationChannelScreen
import cz.kiec.idontwanttosee.ui.screen.addrule.AddRuleScreen
import cz.kiec.idontwanttosee.ui.screen.modifyrule.ModifyRuleScreen
import cz.kiec.idontwanttosee.ui.screen.notificationchannels.NotificationChannelsScreen
import cz.kiec.idontwanttosee.ui.screen.rules.RulesScreen

@Composable
fun Application(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        modifier = Modifier,
        navController = navController,
        startDestination = Screen.Rules,
    ) {
        composable<Screen.Rules> {
            RulesScreen(
                navController = navController,
            )
        }

        composable<Screen.AddRule> {
            AddRuleScreen(
                navController = navController,
            )
        }

        composable<Screen.ModifyRule> {
            val route = it.toRoute<Screen.ModifyRule>()

            ModifyRuleScreen(
                id = route.id,
                navController = navController,
            )
        }

        composable<Screen.NotificationChannels> {
            NotificationChannelsScreen(
                navController = navController,
            )
        }

        composable<Screen.AddNotificationChannel> {
            AddNotificationChannelScreen(
                navController = navController,
            )
        }
    }
}