package com.ubersnap.challange.todo.app.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ubersnap.challange.todo.app.entity.Todo

@Entity(tableName = "todo")
data class TodoEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "due_date")
    val dueDate: Long

) {

    fun asTodo() = Todo(
        id = id,
        title = title,
        description = description,
        dueDate = dueDate
    )

}