package com.jerry.project.domain.koin.unitTesting

import com.jerry.project.domain.TodoUseCase
import kotlinx.coroutines.test.runTest
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue
import org.koin.test.inject
import kotlin.test.AfterTest

/**
 * Unit test class for testing the `TodoUseCase` with Koin dependency injection.
 * The test ensures the correct functionality of the `TodoUseCase` methods such as
 * creating and completing todos.
 * The tests are run with mocked dependencies provided by Koin.
 */

class TodoUseCasekoinTest: KoinTest {

    // Injecting mocked dependencies using Koin
    private val useCase by inject<TodoUseCase>()

    // before each test Koin is Initialized and test modules are loaded
    @BeforeTest
    fun setup() {
       startKoin {
           modules(testModule)
       }
    }

    @AfterTest
    fun tearDownKoin(){
        stopKoin()
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

//AAA stands for Arrange, Act, and Assert,
// which is a common pattern used in writing unit tests.
// It helps structure the tests in a clear, logical,
// and consistent way to improve readability and maintainability