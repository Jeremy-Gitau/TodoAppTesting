package com.jerry.project.domain.unitTest

import com.jerry.project.domain.TodoItem
import com.jerry.project.domain.TodoRepository
import com.jerry.project.domain.TodoRepositoryImpl
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class TodoRepositoryTest {
    private lateinit var mockTodoDatabase: MockTodoDatabase
    private lateinit var repository: TodoRepository

    @BeforeTest
    fun setup() {
        mockTodoDatabase = MockTodoDatabase()
        repository = TodoRepositoryImpl(mockTodoDatabase)
    }

    @Test
    fun addTodo() = runTest {
        // Given
        val todo = TodoItem("1", "Test Todo", "empty")

        // When
        repository.addTodo(todo)
        val todos = repository.getAllTodos()

        // Then
        assertEquals(1, todos.size)
        assertEquals(todo, todos.first())
    }

    @Test
    fun updateTodo() = runTest {
        // Given
        val todo = TodoItem("1", "Test Todo", "empty")
        repository.addTodo(todo)

        // When
        val updatedTodo = todo.copy(isCompleted = true)
        repository.updateTodo(updatedTodo)
        val todos = repository.getAllTodos()

        // Then
        assertEquals(1, todos.size)
        assertEquals(updatedTodo, todos.first())
    }
}
