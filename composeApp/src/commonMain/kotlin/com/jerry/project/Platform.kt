package com.jerry.project

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform