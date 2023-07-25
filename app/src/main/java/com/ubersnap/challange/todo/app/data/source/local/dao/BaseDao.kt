package com.ubersnap.challange.todo.app.data.source.local.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(obj: T): Long

    @Delete
    suspend fun delete(obj: T)

}