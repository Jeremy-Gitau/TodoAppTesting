package com.jerry.project.domain.platformSpecific

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import com.jerry.project.data.TodoDatabase
import com.jerry.project.data.TodoDatabaseImpl
import com.jerry.project.domain.TodoItem
import com.jerry.project.domain.TodoRepository
import com.jerry.project.domain.TodoRepositoryImpl
import com.jerry.project.ui.TodoScreenModel
import com.jerry.project.ui.TodoState
import com.jerry.project.ui.composables.TodoContent
import org.junit.Rule
import kotlin.test.BeforeTest
import kotlin.test.Test

class DesktopScreenModelTest {

    private lateinit var repository: TodoRepository
    private lateinit var todoDatabase: TodoDatabase
    private lateinit var screenModel: TodoScreenModel

    @get:Rule
    val rule = createComposeRule()

    @BeforeTest
    fun setUp() {
        todoDatabase = TodoDatabaseImpl()
        repository = TodoRepositoryImpl(todoDatabase)
        screenModel = TodoScreenModel(repository)
    }


    @Test
    fun shouldAddNewTodoItem()  {
        rule.setContent{
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

        rule.onNodeWithText("Title: Clean the house").assertExists()
        rule.onNodeWithText("Description: Vacuum, mop, dust").assertExists()

    }


}