package com.jerry.project

import com.jerry.project.data.TodoDatabase
import com.jerry.project.data.TodoDatabaseImpl
import com.jerry.project.domain.TodoRepository
import com.jerry.project.domain.TodoRepositoryImpl
import com.jerry.project.domain.TodoUseCase
import com.jerry.project.ui.TodoScreenModel
import org.koin.dsl.module

/**
 * This module defines the dependency injection setup for the application using Koin.
 * It provides instances of the database, repository, use case, and ViewModel that
 * are required for the Todo application. The `appModule` ensures that the dependencies
 * are properly instantiated and injected into the appropriate classes.
 */

val appModule = module {

    // Provides a singleton instance of TodoDatabase
    single<TodoDatabase> { TodoDatabaseImpl() }

    // Provides a singleton instance of TodoRepository with a dependency on TodoDatabase
    single<TodoRepository>{ TodoRepositoryImpl(get() ) }

    // Provides a singleton instance of TodoUseCase with a dependency on TodoRepository
    single { TodoUseCase(get()) }

    // Provides a singleton instance of TodoScreenModel with a dependency on TodoUseCase
    single { TodoScreenModel(get() ) }
}
