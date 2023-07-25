package com.ubersnap.challange.todo.app.entity

import com.ubersnap.challange.todo.app.data.source.local.entity.TodoEntity

data class Todo(
    val id: Long,
    val title: String,
    val description: String,
    val dueDate: Long
) {

    fun asTodoEntity() = TodoEntity(
        id = id,
        title = title,
        description = description,
        dueDate = dueDate
    )

}
