package com.example.talktome

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddTalkScreen(
    onSave: (String, Int) -> Unit,
    onCancel: () -> Unit
) {
    var message by remember { mutableStateOf("") }
    var interval by remember { mutableStateOf("15") }
    Column(modifier = Modifier.padding(16.dp)) {
        TopAppBar(title = { Text("Add Talk") })
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            value = message,
            onValueChange = { message = it },
            label = { Text("Message to Speak") }
        )
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            value = interval,
            onValueChange = { interval = it },
            label = { Text("Interval minutes") }
        )
        Spacer(Modifier.height(16.dp))
        Row {
            Button(onClick = { onSave(message, interval.toIntOrNull() ?: 15) }) {
                Text("Save")
            }
            Spacer(Modifier.width(8.dp))
            Button(onClick = onCancel) {
                Text("Cancel")
            }
        }
    }
}
