package com.jerry.project.ui

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.jerry.project.domain.TodoItem
import com.jerry.project.domain.TodoRepository
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * This class represents the voyager ScreenModel
 * for managing the state of Todo items in the UI.
 * It handles the business logic for loading, adding, updating, and deleting Todo items.
 * The `TodoScreenModel` interacts with the repository to perform these operations and
 * updates the UI state accordingly.
 */

data class TodoState(
    val todos: List<TodoItem> = emptyList(),
    var newTodo: TodoItem? = null,
)

class TodoScreenModel(private val repository: TodoRepository) :
    StateScreenModel<TodoState>(initialState = TodoState()) {

    init {
        loadTodos()
    }

    // Load all todos from the repository
    fun loadTodos() {
        screenModelScope.launch {
            mutableState.value = state.value.copy(
                todos = repository.getAllTodos()
            )
        }
    }

    fun setNewTodo(title: String, description: String) {
        mutableState.value = state.value.copy(
            newTodo = TodoItem(
                id = generateTodoId(),
                title = title,
                description = description
            )
        )
    }

    fun addTodo(item: TodoItem) {
        screenModelScope.launch {
                repository.addTodo(item)
                mutableState.value = state.value.copy(
                    newTodo = null,
                    todos = repository.getAllTodos()
                )

        }
    }

    fun updateTodo(todo: TodoItem) {
        screenModelScope.launch {
            repository.updateTodo(todo)
            loadTodos() // Refresh the list after updating
        }
    }

    fun deleteTodo(todo: TodoItem) {
        screenModelScope.launch {
            repository.deleteTodo(todo.id)
            loadTodos() // Refresh the list after deletion
        }
    }

    // Generate a unique ID for new Todo items
    @OptIn(ExperimentalUuidApi::class)
    private fun generateTodoId() = "todo-${Uuid.random()}"

}