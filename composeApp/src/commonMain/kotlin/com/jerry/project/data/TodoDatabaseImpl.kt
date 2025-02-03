package com.jerry.project.data

import com.jerry.project.domain.TodoItem

/**
 * This class provides an in-memory implementation
 * of the TodoDatabase interface for managing todo items.
 * It allows insertion, retrieval, updating, and deletion
 * of TodoItem objects using a mutable map as storage.
 */

class TodoDatabaseImpl : TodoDatabase {

    private val todos = mutableMapOf<String, TodoItem>()

    override suspend fun insert(todo: TodoItem) {
        todos[todo.id] = todo
    }

    override suspend fun getById(id: String): TodoItem? {
        return todos[id]
    }

    override suspend fun getAll(): List<TodoItem> {
        return todos.values.toList()
    }

    override suspend fun update(todo: TodoItem) {
        todos[todo.id] = todo
    }

    override suspend fun delete(id: String) {
        todos.remove(id)
    }

}