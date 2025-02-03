package com.jerry.project.domain.integrationTest

import com.jerry.project.data.TodoDatabase
import com.jerry.project.data.TodoDatabaseImpl
import com.jerry.project.domain.TodoRepository
import com.jerry.project.domain.TodoRepositoryImpl
import com.jerry.project.domain.TodoUseCase
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 * Integration tests for the Todo application that verify the entire flow of creating,
 * updating, and retrieving Todo items. These tests cover multiple layers (database,
 * repository, use case) working together to ensure proper functionality.
 */

class TodoIntegrationTest {
    private lateinit var database: TodoDatabase
    private lateinit var repository: TodoRepository
    private lateinit var useCase: TodoUseCase

    @BeforeTest
    fun setup() {
        // Initialize all components
        database = TodoDatabaseImpl()
        repository = TodoRepositoryImpl(database)
        useCase = TodoUseCase(repository)
    }

    @Test
    fun testCreateAndCompleteTodo() = runTest {
        // Test the full flow of creating and completing a todo

        // Create a new todo
        val todo = useCase.addTodo(
            title = "Write integration tests",
            description = "Create comprehensive integration tests for the Todo app"
        )

        // Verify it was saved correctly
        val savedTodo = useCase.getTodoDetails(todo.id)

        assertNotNull(savedTodo)
        assertEquals(todo.title, savedTodo.title)
        assertEquals(todo.description, savedTodo.description)
        assertFalse(savedTodo.isCompleted)

        // mark todo item as Complete
        val completedTodo = useCase.completeTodo(todo.id)
        assertNotNull(completedTodo)
        assertTrue(completedTodo.isCompleted)

        // Verify the completed state was saved
        val finalTodo = useCase.getTodoDetails(todo.id)
        assertNotNull(finalTodo)
        assertTrue(finalTodo.isCompleted)
    }

    @Test
    fun testNonExistentTodo() = runTest {
        // This test verifies how our layers handle missing data
        val result = useCase.completeTodo("non-existent-id")
        assertNull(result)
    }

}