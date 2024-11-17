package com.example.myapplication.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(viewModel: ProfileViewModel) {
    var showDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            // Display current profile details
            Text(text = "Full Name: ${viewModel.fullName}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Position: ${viewModel.position}", style = MaterialTheme.typography.bodyLarge)

            // Avatar Image
            Image(
                painter = painterResource(id = R.drawable.ic_avatar_placeholder),
                contentDescription = stringResource(id = R.string.avatar_description),
                modifier = Modifier
                    .size(100.dp)
                    .padding(top = 16.dp)
            )
        }

        // Edit Button positioned at the top-right corner
        IconButton(
            onClick = { showDialog = true },
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_edit),
                contentDescription = stringResource(id = R.string.edit_profile)
            )
        }

        // Dialog for editing profile
        if (showDialog) {
            EditProfileDialog(
                viewModel = viewModel,
                onDismiss = { showDialog = false }
            )
        }
    }
}

@Composable
fun EditProfileDialog(viewModel: ProfileViewModel, onDismiss: () -> Unit) {
    var newName by remember { mutableStateOf(viewModel.fullName) }
    var newPosition by remember { mutableStateOf(viewModel.position) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(onClick = {
                viewModel.fullName = newName
                viewModel.position = newPosition
                onDismiss()
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Cancel")
            }
        },
        title = { Text("Edit Profile") },
        text = {
            Column {
                OutlinedTextField(
                    value = newName,
                    onValueChange = { newName = it },
                    label = { Text("Full Name") },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = newPosition,
                    onValueChange = { newPosition = it },
                    label = { Text("Position") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    )
}

@Preview
@Composable
fun ProfileScreenPreview() {
    val viewModel = ProfileViewModel()
    ProfileScreen(viewModel = viewModel)
}
