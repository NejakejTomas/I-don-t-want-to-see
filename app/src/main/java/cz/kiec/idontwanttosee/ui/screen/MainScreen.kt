package cz.kiec.idontwanttosee.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import cz.kiec.idontwanttosee.R
import cz.kiec.idontwanttosee.ui.navigation.Rules
import cz.kiec.idontwanttosee.ui.navigation.ScreenControllable

@Composable
fun MainScreen(
    controllable: ScreenControllable,
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    controllable.Title(stringResource(R.string.app_name))

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            navController.navigate(Rules)
        })
        {
            Text(stringResource(R.string.rules))
        }
    }
}