package cz.kiec.idontwanttosee.ui.screen

import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
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
import cz.kiec.idontwanttosee.viewmodel.AddRuleViewModel
import org.koin.androidx.compose.koinViewModel

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
fun AddRuleScreen(
    setScreenDecors: @Composable (@Composable ScreenDecors.() -> ScreenDecors) -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier,
    addRuleViewModel: AddRuleViewModel = koinViewModel(),
) {
    setScreenDecors {
        copy(title = stringResource(R.string.top_bar_title_add_rule))
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

    val uiState by addRuleViewModel.uiState.collectAsStateWithLifecycle()

    Column(verticalArrangement = Arrangement.SpaceBetween) {
        RuleEdit(
            modifier,
            uiState.packageName,
            uiState.ignoreOngoing,
            uiState.ignoreWithProgressBar,
            uiState.hideTitle,
            uiState.hideContent,
            uiState.hideLargeImage,
            addRuleViewModel::setPackageName,
            addRuleViewModel::setIgnoreOngoing,
            addRuleViewModel::setIgnoreWithProgressBar,
            addRuleViewModel::setHideTitle,
            addRuleViewModel::setHideContent,
            addRuleViewModel::setHideLargeImage,
        )

        Button(
            onClick = {
                addRuleViewModel.save()
                navController.navigateUp()
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(stringResource(R.string.add_rule_screen_add))
        }
    }
}