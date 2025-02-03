package com.jerry.project.domain.unitTest

import com.jerry.project.data.TodoDatabase
import com.jerry.project.domain.TodoItem
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

// First, let's add a mock database for isolation
class MockTodoDatabase : TodoDatabase {
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


class TodoUseCaseTest {
    private lateinit var mockTodoDatabase: MockTodoDatabase
    private lateinit var repository: TodoRepository
    private lateinit var useCase: TodoUseCase

    @BeforeTest
    fun setup() {
        mockTodoDatabase = MockTodoDatabase()
        repository = TodoRepositoryImpl(mockTodoDatabase)
        useCase = TodoUseCase(repository)
    }

    @Test
    fun testCreateTodo() = runTest {
        // Act
        val todo = useCase.addTodo(
            title = "  Test Title  ",
            description = "  Test Description  "
        )

        // Assert
        assertEquals("Test Title", todo.title)
        assertEquals("Test Description", todo.description)
        assertFalse(todo.isCompleted)
    }

    @Test
    fun testCompleteTodo() = runTest {
        // Arrange
        val todo = useCase.addTodo("Test", "Description")

        // Act
        val completedTodo = useCase.completeTodo(todo.id)

        // Assert
        assertNotNull(completedTodo)
        assertTrue(completedTodo.isCompleted)
    }

    @Test
    fun testCompletingNonExistentTodo() = runTest {
        val result = useCase.completeTodo("non-existent")
        assertNull(result)
    }
}
