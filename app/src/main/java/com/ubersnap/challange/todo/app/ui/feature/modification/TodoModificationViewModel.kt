package com.ubersnap.challange.todo.app.ui.feature.modification

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ubersnap.challange.todo.app.data.Resource
import com.ubersnap.challange.todo.app.data.repository.TodoRepository
import com.ubersnap.challange.todo.app.util.valueOrZero
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoModificationViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {

    private var savedTodoId by mutableStateOf<Long?>(null)

    var uiMode by mutableStateOf(UiMode.CREATE)
        private set

    var title by mutableStateOf("")
        private set

    fun onTitleUpdate(newTitle: String) {
        title = newTitle
    }

    var description by mutableStateOf("")
        private set

    fun onDescriptionUpdate(newDescription: String) {
        description = newDescription
    }

    var dueDate by mutableStateOf(0L)

    fun onDueDateUpdate(newDueDate: Long) {
        dueDate = newDueDate
    }

    fun setupTodoId(todoId: Long? = null) {
        if (todoId != null && todoId != 0L) {
            savedTodoId = todoId
            uiMode = UiMode.UPDATE
            getTodo()
        }
    }

    fun onDeleteAction() {

    }

    fun onSaveAction() {

    }

    private fun getTodo() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getTodo(savedTodoId.valueOrZero()).let { resource ->
                when (resource) {
                    is Resource.Success -> {
                        resource.data?.let { todo ->
                            title = todo.title
                            description = todo.description
                            dueDate = todo.dueDate
                        }
                    }
                    is Resource.Failed -> { }
                }
            }
        }
    }

    fun createTodo() {

    }

    fun updateTodo() {

    }

    fun deleteTodo() {

    }

}