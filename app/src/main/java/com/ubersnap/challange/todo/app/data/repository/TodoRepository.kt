package com.ubersnap.challange.todo.app.data.repository

import com.ubersnap.challange.todo.app.data.Resource
import com.ubersnap.challange.todo.app.entity.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    fun getTodos(): Flow<List<Todo>>
    suspend fun getTodo(todoId: Long): Resource<Todo>
    suspend fun createTodo(todo: Todo): Resource<Nothing>
    suspend fun deleteTodo(todo: Todo): Resource<Nothing>
}