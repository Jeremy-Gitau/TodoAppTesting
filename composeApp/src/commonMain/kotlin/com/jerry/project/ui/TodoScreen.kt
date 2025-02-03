package com.jerry.project.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import com.jerry.project.ui.composables.TodoContent

object TodoScreen: Screen {


    @Composable
    override fun Content() {
        val todoScreenModel: TodoScreenModel = koinScreenModel()

        val state by todoScreenModel.state.collectAsState()
        TodoContent(
            state = state,
            screenModel = todoScreenModel
        )
    }
}