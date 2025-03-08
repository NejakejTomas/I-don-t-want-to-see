package cz.kiec.idontwanttosee.ui.elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cz.kiec.idontwanttosee.ui.theme.IDontWantToSeeTheme
import cz.kiec.idontwanttosee.ui.theme.Typography


@Preview
@Composable
private fun AnnotatedTextPreview() {
    IDontWantToSeeTheme {
        Surface(modifier = Modifier.fillMaxWidth()) {
            AnnotatedText("Annotation text", "Text")
        }
    }
}

@Composable
fun AnnotatedText(annotation: String, text: String, modifier: Modifier = Modifier) {
    Column(modifier) {
        Text(
            text = annotation,
            style = Typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = text,
        )
    }
}