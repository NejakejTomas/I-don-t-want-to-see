package cz.kiec.idontwanttosee.ui.screen.addrule

import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import cz.kiec.idontwanttosee.R
import cz.kiec.idontwanttosee.ui.content.RuleEdit
import cz.kiec.idontwanttosee.ui.elements.TopAppBar
import cz.kiec.idontwanttosee.ui.elements.screenContentPadding
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AddRuleScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: AddRuleViewModel = koinViewModel(),
) {
    val notificationPermission = rememberPermissionState(
        permission = Manifest.permission.POST_NOTIFICATIONS
    )

    LaunchedEffect(notificationPermission) {
        if (!notificationPermission.status.isGranted && notificationPermission.status.shouldShowRationale) {
            // Show rationale if needed
        } else {
            notificationPermission.launchPermissionRequest()
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = stringResource(R.string.top_bar_title_add_rule),
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
            var selectedText by remember { mutableStateOf("aaa") }

            RuleEdit(
                Modifier.weight(1f),
                uiState.packageName,
                uiState.isEnabled,
                uiState.ignoreOngoing,
                uiState.ignoreWithProgressBar,
                uiState.hideTitle,
                uiState.hideContent,
                uiState.hideLargeImage,
                listOf("aaa", "bbb", "ccc"),
                selectedText,
                viewModel::setPackageName,
                viewModel::setIsEnabled,
                viewModel::setIgnoreOngoing,
                viewModel::setIgnoreWithProgressBar,
                viewModel::setHideTitle,
                viewModel::setHideContent,
                viewModel::setHideLargeImage,
                { selectedText = it },
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