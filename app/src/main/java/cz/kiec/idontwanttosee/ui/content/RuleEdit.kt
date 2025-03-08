package cz.kiec.idontwanttosee.ui.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Abc
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import cz.kiec.idontwanttosee.R
import cz.kiec.idontwanttosee.ui.Dimens


@Composable
private fun AnnotatedCheckbox(
    annotation: String,
    icon: ImageVector,
    checked: Boolean,
    onCheckedChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null)
        Spacer(modifier = Modifier.width(Dimens.D10))
        Text(text = annotation, modifier = Modifier.weight(1f, true))
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChanged
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RuleEdit(
    modifier: Modifier = Modifier,
    packageName: String,
    isEnabled: Boolean,
    ignoreOngoing: Boolean,
    ignoreWithProgressBar: Boolean,
    hideTitle: Boolean,
    hideContent: Boolean,
    hideLargeImage: Boolean,
    notificationChannels: List<String>,
    selectedNotificationChannel: String,
    onPackageNameChange: (String) -> Unit,
    onEnabledChange: (Boolean) -> Unit,
    onIgnoreOngoingChange: (Boolean) -> Unit,
    onIgnoreWithProgressBarChange: (Boolean) -> Unit,
    onHideTitleChange: (Boolean) -> Unit,
    onHideContentChange: (Boolean) -> Unit,
    onHideLargeImageChange: (Boolean) -> Unit,
    onSelectedNotificationChannel: (String) -> Unit,
) {
    LazyColumn(modifier) {
        item {
            TextField(
                value = packageName,
                onValueChange = onPackageNameChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(R.string.rule_filter_package_name)) })
        }

        item {
            AnnotatedCheckbox(
                stringResource(R.string.rule_filter_is_enabled),
                Icons.Filled.CheckCircleOutline,
                isEnabled,
                onEnabledChange,
                Modifier.fillMaxWidth(),
            )
        }

        item {
            AnnotatedCheckbox(
                stringResource(R.string.rule_filter_ignore_ongoing),
                ImageVector.vectorResource(R.drawable.ic_progressbar_ongoing),
                ignoreOngoing,
                onIgnoreOngoingChange,
                Modifier.fillMaxWidth(),
            )
        }

        item {
            AnnotatedCheckbox(
                stringResource(R.string.rule_filter_ignore_with_progressbar),
                ImageVector.vectorResource(R.drawable.ic_progressbar),
                ignoreWithProgressBar,
                onIgnoreWithProgressBarChange,
                Modifier.fillMaxWidth(),
            )
        }

        item {
            HorizontalDivider()
        }

        item {
            AnnotatedCheckbox(
                stringResource(R.string.rule_action_hide_title),
                Icons.Filled.Title,
                hideTitle,
                onHideTitleChange,
                Modifier.fillMaxWidth(),
            )
        }

        item {
            AnnotatedCheckbox(
                stringResource(R.string.rule_action_hide_content),
                Icons.Filled.Abc,
                hideContent,
                onHideContentChange,
                Modifier.fillMaxWidth(),
            )
        }

        item {
            AnnotatedCheckbox(
                stringResource(R.string.rule_action_hide_image),
                Icons.Filled.Image,
                hideLargeImage,
                onHideLargeImageChange,
                Modifier.fillMaxWidth(),
            )
        }


//        item {
//            var showDropdown by rememberSaveable { mutableStateOf(false) }
//
//            ExposedDropdownMenuBox(
//                expanded = showDropdown,
//                onExpandedChange = {
//                    showDropdown = !showDropdown
//                }
//            ) {
//                TextField(
//                    value = selectedNotificationChannel,
//                    onValueChange = {},
//                    readOnly = true,
//                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = showDropdown) },
//                    modifier = Modifier.menuAnchor()
//                )
//
//                ExposedDropdownMenu(
//                    expanded = showDropdown,
//                    onDismissRequest = { showDropdown = false }
//                ) {
//                    notificationChannels.forEach { item ->
//                        DropdownMenuItem(
//                            text = { Text(text = item) },
//                            onClick = {
//                                onSelectedNotificationChannel(item)
//                                showDropdown = false
//                            }
//                        )
//                    }
//                }
//            }
//        }
    }


//    Column(
//        modifier
//            .verticalScroll(rememberScrollState())
//    ) {
//        TextField(
//            value = packageName,
//            onValueChange = onPackageNameChange,
//            modifier = Modifier.fillMaxWidth(),
//            label = { Text(stringResource(R.string.rule_filter_package_name)) })
//
//        AnnotatedCheckbox(
//            stringResource(R.string.rule_filter_ignore_ongoing),
//            painterResource(id = R.drawable.ic_progressbar_ongoing),
//            ignoreOngoing,
//            onIgnoreOngoingChange,
//            Modifier.fillMaxWidth(),
//        )
//
//        AnnotatedCheckbox(
//            stringResource(R.string.rule_filter_ignore_with_progressbar),
//            painterResource(id = R.drawable.ic_progressbar),
//            ignoreWithProgressBar,
//            onIgnoreWithProgressBarChange,
//            Modifier.fillMaxWidth(),
//        )
//
//        HorizontalDivider()
//
//        AnnotatedCheckbox(
//            stringResource(R.string.rule_action_hide_title),
//            painterResource(id = R.drawable.ic_title),
//            hideTitle,
//            onHideTitleChange,
//            Modifier.fillMaxWidth(),
//        )
//
//        AnnotatedCheckbox(
//            stringResource(R.string.rule_action_hide_content),
//            painterResource(id = R.drawable.ic_text),
//            hideContent,
//            onHideContentChange,
//            Modifier.fillMaxWidth(),
//        )
//
//        AnnotatedCheckbox(
//            stringResource(R.string.rule_action_hide_image),
//            painterResource(id = R.drawable.ic_image),
//            hideLargeImage,
//            onHideLargeImageChange,
//            Modifier.fillMaxWidth(),
//        )
//    }
}