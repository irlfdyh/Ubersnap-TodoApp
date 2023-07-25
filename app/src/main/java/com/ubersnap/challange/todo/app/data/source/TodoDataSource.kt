package com.ubersnap.challange.todo.app.data.source

import com.ubersnap.challange.todo.app.data.Resource
import com.ubersnap.challange.todo.app.data.repository.TodoRepository
import com.ubersnap.challange.todo.app.data.source.local.dao.TodoDao
import com.ubersnap.challange.todo.app.entity.Todo
import com.ubersnap.challange.todo.app.util.asTodos
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class TodoDataSource @Inject constructor(
    private val todoDao: TodoDao
) : TodoRepository {

    override fun getTodos(): Flow<List<Todo>> {
        return todoDao.getTodos()
            .map { todoEntities ->
                todoEntities.asTodos()
            }
    }

    override suspend fun getTodo(todoId: Long): Resource<Todo> {
        return try {
            Resource.Success(
                data = todoDao.getTodo(todoId).asTodo()
            )
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Failed(
                message = e.message
            )
        }
    }

    override suspend fun createTodo(todo: Todo): Resource<Nothing> {
        return try {
            todoDao.insert(todo.asTodoEntity())
            Resource.Success()
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Failed(
                message = e.message
            )
        }
    }

    override suspend fun updateTodo(todo: Todo): Resource<Nothing> {
        return try {
            todoDao.update(todo.asTodoEntity())
            Resource.Success()
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Failed(
                message = e.message
            )
        }
    }

    override suspend fun deleteTodo(todo: Todo): Resource<Nothing> {
        return try {
            todoDao.delete(todo.asTodoEntity())
            Resource.Success()
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Failed(
                message = e.message
            )
        }
    }

}