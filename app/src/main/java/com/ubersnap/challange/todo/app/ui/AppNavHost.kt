package com.ubersnap.challange.todo.app.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ubersnap.challange.todo.app.ui.feature.list.TodoListScreen
import com.ubersnap.challange.todo.app.ui.feature.modification.TodoModificationScreen

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
            TodoListScreen(
                onTodoItemClick = { id ->
                    navController.navigate(TodoModification.buildRouteWithArgs(id))
                },
                onCreateTodoClick = {
                    navController.navigate(TodoModification.buildRouteWithArgs())
                }
            )
        }

        composable(
            route = TodoModification.routeWithArgs,
            arguments = TodoModification.arguments
        ) { navBackStackEntry ->
            val todoId = navBackStackEntry.arguments?.getLong(TodoModification.todoIdKey)
            TodoModificationScreen(
                todoId = todoId,
                onNavigateUp = navController::navigateUp
            )
        }

    }
}