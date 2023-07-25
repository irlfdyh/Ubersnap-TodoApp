package com.ubersnap.challange.todo.app.di

import com.ubersnap.challange.todo.app.data.repository.TodoRepository
import com.ubersnap.challange.todo.app.data.source.TodoDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class TodoModule {
    @Binds
    abstract fun bindTodoRepository(source: TodoDataSource): TodoRepository
}