package com.ubersnap.challange.todo.app.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: AppDestination,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.route,
        modifier = modifier
    ) {

        composable(route = Todo.route) {

        }

        composable(
            route = TodoModification.routeWithArgs,
            arguments = TodoModification.arguments
        ) { navBackStackEntry ->
            val todoId = navBackStackEntry.arguments?.getLong(TodoModification.todoIdKey)

        }

        composable(
            route = TodoDetail.routeWithArgs,
            arguments = TodoDetail.arguments
        ) { navBackStackEntry ->
            val todoId = navBackStackEntry.arguments?.getLong(TodoDetail.todoId)
        }

    }
}