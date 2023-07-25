package com.ubersnap.challange.todo.app.ui

import androidx.navigation.NavType
import androidx.navigation.navArgument

interface AppDestination {
    val route: String
}

object Todo : AppDestination {
    override val route: String
        get() = "todo"
}

object TodoModification : AppDestination {
    override val route: String
        get() = "todo/modification"
    const val todoIdKey = "todo_id"
    val routeWithArgs = "$route?${todoIdKey}={$todoIdKey}"
    val arguments = listOf(
        navArgument(todoIdKey) {
            type = NavType.LongType
            defaultValue = 0L
        }
    )

    fun buildRouteWithArgs(
        todoId: Long? = null
    ) = "$route?$todoIdKey=$todoId"
}
