package cz.kiec.idontwanttosee.ui.screen

import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import cz.kiec.idontwanttosee.R
import cz.kiec.idontwanttosee.ui.Dimens
import cz.kiec.idontwanttosee.ui.screen.content.RuleEdit
import cz.kiec.idontwanttosee.viewmodel.ModifyRuleViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
private fun AnnotatedCheckbox(
    annotation: String,
    icon: Painter,
    checked: Boolean,
    onCheckedChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painter = icon, contentDescription = null)
        Spacer(modifier = Modifier.width(Dimens.D10))
        Text(text = annotation, modifier = Modifier.weight(1f, true))
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChanged
        )
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ModifyRuleScreen(
    id: Long,
    setScreenDecors: @Composable (@Composable ScreenDecors.() -> ScreenDecors) -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier,
    modifyRuleViewModel: ModifyRuleViewModel = koinViewModel(
        parameters = { parametersOf(id) }),
) {
    setScreenDecors {
        copy(title = stringResource(R.string.top_bar_title_modify_rule))
    }

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

    val uiState by modifyRuleViewModel.uiState.collectAsStateWithLifecycle()

    RuleEdit(
        modifier,
        uiState.packageName,
        uiState.ignoreOngoing,
        uiState.ignoreWithProgressBar,
        uiState.hideTitle,
        uiState.hideContent,
        uiState.hideLargeImage,
        modifyRuleViewModel::setPackageName,
        modifyRuleViewModel::setIgnoreOngoing,
        modifyRuleViewModel::setIgnoreWithProgressBar,
        modifyRuleViewModel::setHideTitle,
        modifyRuleViewModel::setHideContent,
        modifyRuleViewModel::setHideLargeImage,
    )
}