package com.example.talktome

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun TalkListScreen(viewModel: TalkViewModel = hiltViewModel()) {
    val talks by viewModel.talks.collectAsState()
    var adding by remember { mutableStateOf(false) }

    if (adding) {
        AddTalkScreen(
            onSave = { msg, interval ->
                viewModel.addTalk(msg, interval)
                adding = false
            },
            onCancel = { adding = false }
        )
    } else {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = { adding = true }) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }
        ) { padding ->
            LazyColumn(modifier = Modifier.padding(padding)) {
                items(talks) { talk ->
                    TalkRow(
                        talk,
                        onToggle = { viewModel.toggleEnabled(talk, it) },
                        onClick = { viewModel.speak(talk) }
                    )
                }
            }
        }
    }
}

@Composable
fun TalkRow(talk: Talk, onToggle: (Boolean) -> Unit, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = talk.message)
            Text(text = talk.triggerType.name)
        }
        Switch(checked = talk.enabled, onCheckedChange = onToggle)
    }
}
