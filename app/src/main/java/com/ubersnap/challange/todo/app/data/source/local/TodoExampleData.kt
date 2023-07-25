package com.ubersnap.challange.todo.app.data.source.local

import com.ubersnap.challange.todo.app.data.source.local.entity.TodoEntity

object TodoExampleData {

    val todos = listOf(
        TodoEntity(
            id = 1,
            title = "Disk Clean up",
            description = "Clean up my friend disk on their house",
            dueDate = 1690282564000
        ),
        TodoEntity(
            id = 1,
            title = "Finish code challenge",
            description = "Finish code challange from Ubersnap",
            dueDate = 1690279200000
        )
    )

}