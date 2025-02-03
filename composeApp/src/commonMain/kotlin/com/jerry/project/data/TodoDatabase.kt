package com.jerry.project.data

import com.jerry.project.domain.TodoItem

interface TodoDatabase {

    suspend fun insert(todo: TodoItem)
    suspend fun getById(id: String): TodoItem?
    suspend fun getAll(): List<TodoItem>
    suspend fun update(todo: TodoItem)
    suspend fun delete(id: String)

}