package com.jerry.project.domain

import com.jerry.project.data.TodoDatabase

/**
 * This class implements the TodoRepository interface,
 * acting as an intermediary between
 * the application's data layer and the TodoDatabase.
 * It provides methods to add, retrieve,
 * update, and delete TodoItem entries by interacting with the underlying database.
 */

class TodoRepositoryImpl(private val database: TodoDatabase) : TodoRepository {

    override suspend fun addTodo(todo: TodoItem) = database.insert(todo)

    override suspend fun getAllTodos(): List<TodoItem> = database.getAll()

    override suspend fun getTodo(id: String): TodoItem? = database.getById(id)

    override suspend fun updateTodo(todo: TodoItem) = database.update(todo)

    override suspend fun deleteTodo(id: String) = database.delete(id)

}