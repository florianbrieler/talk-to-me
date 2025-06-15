package com.example.talktome

import androidx.compose.foundation.combinedClickable
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
import android.util.Log

@Composable
fun TalkListScreen(viewModel: TalkViewModel = hiltViewModel()) {
    Log.d(TAG, "TalkListScreen called")
    val talks by viewModel.talks.collectAsState()
    var adding by remember { mutableStateOf(false) }
    var deleting by remember { mutableStateOf<Talk?>(null) }

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
                        onClick = { viewModel.speak(talk) },
                        onLongPress = { deleting = talk }
                    )
                }
            }
        }
        deleting?.let { talk ->
            AlertDialog(
                onDismissRequest = { deleting = null },
                title = { Text("Delete Talk") },
                text = { Text("Delete '${'$'}{talk.message}'?") },
                confirmButton = {
                    TextButton(onClick = {
                        viewModel.deleteTalk(talk)
                        deleting = null
                    }) {
                        Text("Delete")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { deleting = null }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}

@Composable
fun TalkRow(
    talk: Talk,
    onToggle: (Boolean) -> Unit,
    onClick: () -> Unit,
    onLongPress: () -> Unit
) {
    Log.d(TAG, "TalkRow called for talk=${'$'}{talk.id}")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(onClick = onClick, onLongClick = onLongPress)
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

private const val TAG = "TalkListScreen"
