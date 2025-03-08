package cz.kiec.idontwanttosee.ui.screen.addnotificationchannel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import cz.kiec.idontwanttosee.R
import cz.kiec.idontwanttosee.ui.content.NotificationChannelEdit
import cz.kiec.idontwanttosee.ui.elements.TopAppBar
import cz.kiec.idontwanttosee.ui.elements.screenContentPadding
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddNotificationChannelScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: AddNotificationChannelViewModel = koinViewModel(),
) {

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = stringResource(R.string.top_bar_title_add_channel),
                navController = navController,
            )
        },
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .padding(innerPadding)
                .screenContentPadding()
        ) {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            NotificationChannelEdit(
                modifier.weight(1f),
                uiState.id,
                uiState.name,
                viewModel::setName
            )

            Button(
                onClick = {
                    viewModel.save()
                    navController.navigateUp()
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(stringResource(R.string.add_rule_screen_add))
            }
        }
    }
}