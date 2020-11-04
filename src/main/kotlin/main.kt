
import androidx.compose.desktop.Window
import androidx.compose.foundation.Box
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.preferredWidth
import androidx.compose.foundation.lazy.ExperimentalLazyDsl
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalLazyDsl::class)
fun main() = Window(title = "Compose Desktop Demo") {
    var textFieldValue by remember { mutableStateOf(TextFieldValue()) }
    var items by remember { mutableStateOf(emptyList<String>()) }

    MaterialTheme {
        LazyColumn(contentPadding = PaddingValues(16.dp)) {
            item {
                Column {
                    Text(
                        text = "Compose Desktop Demo",
                        style = MaterialTheme.typography.h3,
                    )
                    Spacer(modifier = Modifier.preferredHeight(8.dp))
                    Text(
                        text = """
                            This is a quick demo of JetBrains Compose for Desktop shown through a simple notes app.
                        """.trimIndent(),
                        style = MaterialTheme.typography.subtitle1,
                    )
                    Spacer(modifier = Modifier.preferredHeight(16.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxHeight(),
                    ) {
                        OutlinedTextField(
                            value = textFieldValue,
                            onValueChange = {
                                if (!it.text.endsWith("\n")) {
                                    textFieldValue = it
                                }
                            },
                            label = { Text(text = "Add a note") },
                            imeAction = ImeAction.Done,
                            onImeActionPerformed = { imeAction, _ ->
                                if (imeAction == ImeAction.Done) {
                                    items += textFieldValue.text
                                    textFieldValue = TextFieldValue()
                                }
                            },
                        )
                        Spacer(modifier = Modifier.preferredWidth(8.dp))
                        Button(
                            onClick = {
                                items += textFieldValue.text
                                textFieldValue = TextFieldValue()
                            },
                            enabled = textFieldValue.text.isNotEmpty(),
                        ) {
                            Text(text = "Submit")
                        }
                    }
                }
            }
            spacerItem(height = 16.dp)
            items(items = items) {
                Note(text = it)
            }
        }
    }
}

@Composable
private fun Note(text: String) {
    var isChecked by remember { mutableStateOf(false) }
    Box(modifier = Modifier.padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = { isChecked = it },
            )
            Spacer(modifier = Modifier.preferredWidth(4.dp))
            Text(
                text = text,
                modifier = Modifier
                    .clickable(
                        indication = null,
                        onClick = { isChecked = !isChecked }
                    ),
                style = MaterialTheme.typography.body1,
            )
        }
    }
}

private fun LazyListScope.spacerItem(height: Dp) {
    item { Spacer(modifier = Modifier.preferredHeight(height)) }
}