package cz.kiec.idontwanttosee.ui.screen.modifyrule

import android.Manifest
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ModifyRuleScreen(
    id: Long,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: ModifyRuleViewModel = koinViewModel(
        parameters = { parametersOf(id) }),
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
                title = stringResource(R.string.top_bar_title_modify_rule),
                navController = navController,
            )
        },
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        RuleEdit(
            Modifier
                .padding(innerPadding)
                .screenContentPadding(),
            uiState.packageName,
            uiState.isEnabled,
            uiState.ignoreOngoing,
            uiState.ignoreWithProgressBar,
            uiState.hideTitle,
            uiState.hideContent,
            uiState.hideLargeImage,
            listOf("aaa", "bbb", "ccc"),
            "aaa",
            viewModel::setPackageName,
            viewModel::setIsEnabled,
            viewModel::setIgnoreOngoing,
            viewModel::setIgnoreWithProgressBar,
            viewModel::setHideTitle,
            viewModel::setHideContent,
            viewModel::setHideLargeImage,
            { println(it) },
        )

    }
}