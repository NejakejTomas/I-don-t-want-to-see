package cz.kiec.idontwanttosee.ui.screen.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import cz.kiec.idontwanttosee.R
import cz.kiec.idontwanttosee.ui.Dimens


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

@Composable
fun RuleEdit(
    modifier: Modifier = Modifier,
    packageName: String,
    ignoreOngoing: Boolean,
    ignoreWithProgressBar: Boolean,
    hideTitle: Boolean,
    hideContent: Boolean,
    hideLargeImage: Boolean,
    onPackageNameChange: (String) -> Unit,
    onIgnoreOngoingChange: (Boolean) -> Unit,
    onIgnoreWithProgressBarChange: (Boolean) -> Unit,
    onHideTitleChange: (Boolean) -> Unit,
    onHideContentChange: (Boolean) -> Unit,
    onHideLargeImageChange: (Boolean) -> Unit,
) {
    Column(
        modifier
            .verticalScroll(rememberScrollState())
    ) {
        TextField(
            value = packageName,
            onValueChange = onPackageNameChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(stringResource(R.string.package_name)) })

        AnnotatedCheckbox(
            stringResource(R.string.ignore_ongoing),
            painterResource(id = R.drawable.ic_progressbar_ongoing),
            ignoreOngoing,
            onIgnoreOngoingChange,
            Modifier.fillMaxWidth(),
        )

        AnnotatedCheckbox(
            stringResource(R.string.ignore_with_progressbar),
            painterResource(id = R.drawable.ic_progressbar),
            ignoreWithProgressBar,
            onIgnoreWithProgressBarChange,
            Modifier.fillMaxWidth(),
        )

        HorizontalDivider()

        AnnotatedCheckbox(
            stringResource(R.string.hide_title),
            painterResource(id = R.drawable.ic_title),
            hideTitle,
            onHideTitleChange,
            Modifier.fillMaxWidth(),
        )

        AnnotatedCheckbox(
            stringResource(R.string.hide_content),
            painterResource(id = R.drawable.ic_text),
            hideContent,
            onHideContentChange,
            Modifier.fillMaxWidth(),
        )

        AnnotatedCheckbox(
            stringResource(R.string.hide_image),
            painterResource(id = R.drawable.ic_image),
            hideLargeImage,
            onHideLargeImageChange,
            Modifier.fillMaxWidth(),
        )
    }
}