package com.jerry.project.domain


interface TodoRepository {
    suspend fun getAllTodos(): List<TodoItem>
    suspend fun addTodo(todo: TodoItem)
    suspend fun updateTodo(todo: TodoItem)
    suspend fun deleteTodo(id: String)
    suspend fun getTodo(id: String): TodoItem?
}
