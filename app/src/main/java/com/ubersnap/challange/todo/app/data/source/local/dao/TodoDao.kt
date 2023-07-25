package com.ubersnap.challange.todo.app.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ubersnap.challange.todo.app.data.source.local.entity.TodoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao : BaseDao<TodoEntity> {

    @Insert
    suspend fun insertMany(todos: List<TodoEntity>)

    @Query("SELECT * FROM todo")
    fun getTodos(): Flow<List<TodoEntity>>

    @Query("SELECT * FROM todo WHERE todo.id = :todoId")
    suspend fun getTodo(todoId: Long): TodoEntity

}