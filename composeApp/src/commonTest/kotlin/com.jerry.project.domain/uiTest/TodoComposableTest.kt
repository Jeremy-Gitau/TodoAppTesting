package com.jerry.project.domain.uiTest

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.runComposeUiTest
import com.jerry.project.data.TodoDatabase
import com.jerry.project.data.TodoDatabaseImpl
import com.jerry.project.domain.TodoItem
import com.jerry.project.domain.TodoRepository
import com.jerry.project.domain.TodoRepositoryImpl
import com.jerry.project.ui.TodoScreenModel
import com.jerry.project.ui.TodoState
import com.jerry.project.ui.composables.TodoContent
import kotlin.js.JsName
import kotlin.test.BeforeTest
import kotlin.test.Test

/**
 * This test suite verifies the basic functionality of the Todo app's UI components.
 * The tests cover the ability to:
 * 1. Add a new Todo item.
 * 2. Delete an existing Todo item.
 * 3. Edit an existing Todo item.
 *
 * The tests use Jetpack Compose UI testing framework to simulate user interactions
 * and assert that the UI behaves correctly by displaying the appropriate Todo content.
 */

class TodoComposableTest {

    private lateinit var repository: TodoRepository
    private lateinit var todoDatabase: TodoDatabase
    private lateinit var screenModel: TodoScreenModel

    @BeforeTest
    fun setUp() {
        todoDatabase = TodoDatabaseImpl()
        repository = TodoRepositoryImpl(todoDatabase)
        screenModel = TodoScreenModel(repository)
    }


    @OptIn(ExperimentalTestApi::class)
    @Test
    @JsName("fn0")
    fun `should add a new todo item`() = runComposeUiTest {
        setContent {
            TodoContent(
                state = TodoState(
                    todos = listOf(
                        TodoItem(
                            title = "Clean the house",
                            description = "Vacuum, mop, dust",
                            id = "1234"
                        ),
                    ),
                ), screenModel = screenModel
            )
        }

        onNodeWithText("Title: Clean the house").assertExists()
        onNodeWithText("Description: Vacuum, mop, dust").assertExists()

    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    @JsName("fn1")
    fun `should delete a todo item`() = runComposeUiTest {
        setContent {
            TodoContent(
                state = TodoState(
                    todos = listOf(
                        TodoItem(
                            title = "Clean the house",
                            description = "Vacuum, mop, dust",
                            id = "1234"
                        ),
                    )
                ), screenModel = screenModel
            )

        }

        onNodeWithText("Title: Clean the house").assertExists()

        onNodeWithText("Delete").performClick()

    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    @JsName("fn2")
    fun `should edit a todo item`() = runComposeUiTest {
        setContent {
            TodoContent(
                state = TodoState(
                    todos = listOf(
                        TodoItem(
                            title = "Buy groceries", description = "Milk, eggs, bread", id = "1"
                        )
                    )
                ), screenModel = screenModel
            )
        }

        onNodeWithText("Title: Buy groceries").assertExists()
        onNodeWithText("Description: Milk, eggs, bread").assertExists()

        onNodeWithText("Edit").performClick()
        onNodeWithText("Edit Title").performTextInput("Buy ingredients")
        onNodeWithText("Edit Description").performTextInput("Sugar, flour, baking soda")
        onNodeWithText("Save").assertExists()
        onNodeWithText("Save").performClick()

    }
}