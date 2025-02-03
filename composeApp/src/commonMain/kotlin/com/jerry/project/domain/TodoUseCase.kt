package com.jerry.project.domain

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * This class represents a use case layer for managing Todo items.
 * It provides higher-level
 * functions to add, complete, and retrieve details
 * of Todo items by interacting with the
 * repository layer. Each function encapsulates specific business logic,
 * such as generating
 * unique IDs and marking items as completed.
 */

class TodoUseCase(private val repository: TodoRepository) {

    @OptIn(ExperimentalUuidApi::class)
    suspend fun addTodo(title: String, description: String): TodoItem {
        val todo = TodoItem(
            id = Uuid.random().toString(),
            title = title.trim(),
            description =description.trim()
        )
        repository.addTodo(todo)

        return  todo
    }

    suspend fun completeTodo(id: String): TodoItem? {
        val todo = repository.getTodo(id) ?: return null
        val updatedTodo = todo.copy(isCompleted = true)
        repository.updateTodo(updatedTodo)
        return updatedTodo
    }

    suspend fun getTodoDetails(id: String): TodoItem? {
        return repository.getTodo(id)
    }

}