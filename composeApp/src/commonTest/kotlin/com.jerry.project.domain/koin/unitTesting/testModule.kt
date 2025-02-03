package com.jerry.project.domain.koin.unitTesting

import com.jerry.project.data.TodoDatabase
import com.jerry.project.domain.TodoRepository
import com.jerry.project.domain.TodoRepositoryImpl
import com.jerry.project.domain.TodoUseCase
import com.jerry.project.domain.unitTest.MockTodoDatabase
import com.jerry.project.ui.TodoScreenModel
import org.koin.dsl.module

/**
 * Koin module for unit testing dependencies, used to inject mock dependencies
 * into the system to facilitate isolated unit tests. This module provides
 * mock versions of the TodoDatabase and other necessary components for
 * testing purposes.
 */

val testModule = module {
    single<TodoDatabase> { MockTodoDatabase() } // Mock TodoDatabase
    single<TodoRepository> { TodoRepositoryImpl(get()) }
    single { TodoUseCase(get()) }
    single { TodoScreenModel(get()) }
}