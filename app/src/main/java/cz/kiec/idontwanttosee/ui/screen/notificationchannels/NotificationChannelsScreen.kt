package cz.kiec.idontwanttosee.ui.screen.notificationchannels

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import cz.kiec.idontwanttosee.R
import cz.kiec.idontwanttosee.ui.Dimens
import cz.kiec.idontwanttosee.ui.elements.AnnotatedText
import cz.kiec.idontwanttosee.ui.elements.BottomNavigationBar
import cz.kiec.idontwanttosee.ui.elements.TopAppBar
import cz.kiec.idontwanttosee.ui.elements.screenContentPadding
import cz.kiec.idontwanttosee.ui.navigation.BottomNavBarEntries
import cz.kiec.idontwanttosee.ui.navigation.Screen
import org.koin.androidx.compose.koinViewModel

@Composable
fun NotificationChannelsScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: NotificationChannelsViewModel = koinViewModel(),
) {

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = stringResource(R.string.top_bar_title_channels),
                navController = navController,
                bottomNavBarEntries = BottomNavBarEntries.global,
            )
        },
        bottomBar = {
            BottomNavigationBar(navController, BottomNavBarEntries.global)
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Screen.AddNotificationChannel) }) {
                Icon(
                    painter = painterResource(R.drawable.ic_add),
                    contentDescription = stringResource(R.string.floating_button_add_channel_description)
                )
            }
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsStateWithLifecycle(NotificationChannelsUiState())


//    val channel = NotificationChannel(id, name, importanceLevel).apply {
//        description = descriptionText
//    }

        LazyColumn(Modifier
            .padding(innerPadding)
            .screenContentPadding()) {
            items(uiState.channels, key = { it.id }) { channel ->
                val m =
                    Modifier
                        .fillMaxWidth()
                        .let {
                            if (channel != uiState.channels.last()) it.padding(bottom = Dimens.D5) else it
                        }
                ElevatedCard(
                    modifier = m
//                    .combinedClickable(
//                        onClick = { onClick(state) },
//                        onLongClick = { onLongClick(state) },
//                    ),
                ) {
                    Column(Modifier.padding(Dimens.D5)) {
                        AnnotatedText(stringResource(R.string.channel_id), channel.id.toString())
//                    Spacer(Modifier.height(Dimens.D1))
                        AnnotatedText(stringResource(R.string.channel_name), channel.name)
                    }
                }
            }
        }
    }
}