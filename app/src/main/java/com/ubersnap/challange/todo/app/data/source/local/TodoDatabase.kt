package com.ubersnap.challange.todo.app.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ubersnap.challange.todo.app.data.source.local.dao.TodoDao
import com.ubersnap.challange.todo.app.data.source.local.entity.TodoEntity

@Database(
    entities = [
        TodoEntity::class
    ],
    version = TodoDatabase.DATABASE_VERSION,
    exportSchema = false
)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
    companion object {
        const val DATABASE_VERSION = 1
    }
}