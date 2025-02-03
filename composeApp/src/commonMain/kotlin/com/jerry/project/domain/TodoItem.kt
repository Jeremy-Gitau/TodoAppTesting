package com.jerry.project.domain

data class TodoItem(
    val id: String,
    val title: String,
    val description: String,
    val isCompleted: Boolean = false
)
