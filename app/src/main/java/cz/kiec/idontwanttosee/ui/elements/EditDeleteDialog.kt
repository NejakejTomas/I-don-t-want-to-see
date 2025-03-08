package cz.kiec.idontwanttosee.ui.elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import cz.kiec.idontwanttosee.R
import cz.kiec.idontwanttosee.ui.Dimens


@Preview
@Composable
fun EditDeleteDialogPreview() {
    EditDeleteDialog({}, {}, {})
}

@Composable
fun EditDeleteDialog(
    onDismiss: () -> Unit,
    onDelete: () -> Unit,
    onModify: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(Modifier) {
            Column(
                Modifier
                    .padding(Dimens.D7)
                    .width(IntrinsicSize.Max)
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onDismiss()
                        onModify()
                    }) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(painterResource(R.drawable.ic_edit), null)
                        Spacer(modifier = Modifier.width(Dimens.D5))
                        Text(stringResource(R.string.rule_option_edit))
                    }
                }
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onDismiss()
                        onDelete()
                    }) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(painterResource(R.drawable.ic_delete), null)
                        Spacer(modifier = Modifier.width(Dimens.D5))
                        Text(stringResource(R.string.rule_option_delete))
                    }
                }
            }
        }
    }
}