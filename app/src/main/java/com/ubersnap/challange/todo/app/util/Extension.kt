package com.ubersnap.challange.todo.app.util

import com.ubersnap.challange.todo.app.data.source.local.entity.TodoEntity
import com.ubersnap.challange.todo.app.entity.Todo

fun List<TodoEntity>.asTodos(): List<Todo> =
    this.map { it.asTodo() }

fun Long?.valueOrZero() =
    this ?: 0L