package cz.kiec.idontwanttosee.ui.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import cz.kiec.idontwanttosee.R
import cz.kiec.idontwanttosee.ui.Dimens

@Composable
private fun Id(
    modifier: Modifier = Modifier,
    id: Long
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painter = painterResource(id = R.drawable.ic_tag), contentDescription = null)
        Spacer(modifier = Modifier.width(Dimens.D10))
        Text(text = stringResource(R.string.channel_id), modifier = Modifier.weight(1f, true))
    }
}

@Composable
fun NotificationChannelEdit(
    modifier: Modifier = Modifier,
    id: Long?,
    name: String,
    onNameChange: (String) -> Unit,
) {
    Column(
        modifier
            .verticalScroll(rememberScrollState())
    ) {
        if (id != null) Id(modifier, id)

        TextField(
            value = name,
            onValueChange = onNameChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(stringResource(R.string.channel_name)) })
    }
}