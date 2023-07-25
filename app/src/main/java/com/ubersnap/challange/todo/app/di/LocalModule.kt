package com.ubersnap.challange.todo.app.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ubersnap.challange.todo.app.data.source.local.TodoDatabase
import com.ubersnap.challange.todo.app.data.source.local.TodoExampleData
import com.ubersnap.challange.todo.app.data.source.local.dao.TodoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideTodoDatabase(
        @ApplicationContext context: Context,
        todoDao: Provider<TodoDao>
    ) = Room.databaseBuilder(
        context, TodoDatabase::class.java, "todo.db"
    )
        .addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                CoroutineScope(Dispatchers.IO).launch {
                    todoDao.get().insertMany(TodoExampleData.todos)
                }
            }
        })
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideTodoDao(todoDatabase: TodoDatabase) =
        todoDatabase.todoDao()

}