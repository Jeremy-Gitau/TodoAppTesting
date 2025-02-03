package com.jerry.project.domain

import cafe.adriel.voyager.core.model.screenModelScope
import com.jerry.project.domain.unitTest.MockTodoDatabase
import com.jerry.project.ui.TodoScreenModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.js.JsName
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * This test suite verifies the functionality of the TodoScreenModel in the Todo app.
 * The tests check various functionalities like
 * loading, adding, updating, and deleting Todo items
 * to ensure that the screen model behaves as expected
 * when interacting with the repository.
 * It uses coroutine-based tests to simulate asynchronous operations in the ViewModel.
 */

class TodoScreenModelTest {
    private lateinit var repository: TodoRepository
    private lateinit var screenModel: TodoScreenModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeTest
    fun setup() {
        // Set the main dispatcher to Unconfined to avoid dispatching issues in tests
        Dispatchers.setMain(Dispatchers.Unconfined)
        repository = TodoRepositoryImpl(MockTodoDatabase())
        screenModel = TodoScreenModel(repository)
    }

    // Reset the main dispatcher after each test to avoid interfering with other tests
    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterTest
    fun teardown() {
        Dispatchers.resetMain()
    }
//we use the jsname annotation to ensure proper function
// naming when compiled to javascript
// because function naming conventions in kotlin
// and js differ
    @Test
    @JsName("fn0")
    fun `test loadTodos`() = runTest {
        val todos = listOf(
            TodoItem(id = "todo-1", title = "Todo 1", description = "Description 1"),
            TodoItem(id = "todo-2", title = "Todo 2", description = "Description 2")
        )

        repository.addTodo(todos[0])
        repository.addTodo(todos[1])

        screenModel.screenModelScope.run {
            screenModel.loadTodos()
        }

        assertEquals(todos, screenModel.state.value.todos)
    }

    @Test
    @JsName("fn1")
    fun `test addTodo`() = runTest {
        val newTodo = TodoItem(id = "todo-1", title = "New Todo", description = "New Description")

        screenModel.setNewTodo("New Todo", "New Description")

        screenModel.screenModelScope.run {
            screenModel.addTodo(newTodo)
        }

        assertEquals(null, screenModel.state.value.newTodo)
        assertEquals(1, repository.getAllTodos().size)
    }

    @Test
    @JsName("fn2")
    fun `test updateTodo`() = runTest {
        val updatedTodo =
            TodoItem(id = "todo-1", title = "Updated Todo", description = "Updated Description")

        repository.addTodo(TodoItem(id = "todo-1", title = "Todo 1", description = "Description 1"))

        screenModel.screenModelScope.run {
            screenModel.updateTodo(updatedTodo)
        }

        assertEquals(1, repository.getAllTodos().size)
    }

    @Test
    @JsName("fn3")
    fun `test deleteTodo`() = runTest {
        val todoToDelete =
            TodoItem(id = "todo-1", title = "Todo to Delete", description = "Description to Delete")

        repository.addTodo(todoToDelete)

        screenModel.screenModelScope.run {
            screenModel.deleteTodo(todoToDelete)
        }

        assertEquals(0, repository.getAllTodos().size)
    }
}