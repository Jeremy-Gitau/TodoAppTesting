package com.jerry.project.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.benasher44.uuid.UUID
import com.jerry.project.domain.TodoItem
import com.jerry.project.ui.TodoScreenModel
import com.jerry.project.ui.TodoState
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Composable
fun TodoContent(state: TodoState, screenModel: TodoScreenModel) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                if (title.isNotBlank() && description.isNotBlank()) {
                    screenModel.addTodo(TodoItem(
                        title = title,
                        description = description,
                        id = generateUniqueId()
                    ))
                    // Clear the input fields after adding the todo
                    title = ""
                    description = ""
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Add Todo")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display the list of todos
        LazyColumn {
            items(state.todos) { todo ->
                TodoItemRow(
                    todo = todo,
                    onDelete = { screenModel.deleteTodo(todo) },
                    onUpdate = { updatedTodo -> screenModel.updateTodo(updatedTodo) }
                )
            }
        }
    }
}

@Composable
fun TodoItemRow(todo: TodoItem, onDelete: () -> Unit, onUpdate: (TodoItem) -> Unit) {
    var isEditing by remember { mutableStateOf(false) }
    var updatedTitle by remember { mutableStateOf(todo.title) }
    var updatedDescription by remember { mutableStateOf(todo.description) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        if (isEditing) {
            TextField(
                value = updatedTitle,
                onValueChange = { updatedTitle = it },
                label = { Text("Edit Title") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = updatedDescription,
                onValueChange = { updatedDescription = it },
                label = { Text("Edit Description") },
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = {
                        onUpdate(todo.copy(title = updatedTitle, description = updatedDescription))
                        isEditing = false
                    },
                    modifier = Modifier.padding(4.dp)
                ) {
                    Text("Save")
                }
                Button(
                    onClick = { isEditing = false },
                    modifier = Modifier.padding(4.dp)
                ) {
                    Text("Cancel")
                }
            }
        } else {
            Text(text = "Title: ${todo.title}", style = MaterialTheme.typography.h6)
            Text(text = "Description: ${todo.description}", style = MaterialTheme.typography.body1)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = { isEditing = true },
                    modifier = Modifier.padding(4.dp)
                ) {
                    Text("Edit")
                }
                Button(
                    onClick = onDelete,
                    modifier = Modifier.padding(4.dp)
                ) {
                    Text("Delete")
                }
            }
        }
    }
}

// Helper function to generate a unique ID for each TodoItem
@OptIn(ExperimentalUuidApi::class)
private fun generateUniqueId(): String =  Uuid.random().toString()
