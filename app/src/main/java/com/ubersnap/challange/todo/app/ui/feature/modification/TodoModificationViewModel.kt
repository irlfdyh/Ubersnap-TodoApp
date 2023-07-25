package com.ubersnap.challange.todo.app.ui.feature.modification

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ubersnap.challange.todo.app.data.Resource
import com.ubersnap.challange.todo.app.data.repository.TodoRepository
import com.ubersnap.challange.todo.app.entity.Todo
import com.ubersnap.challange.todo.app.ui.LoadState
import com.ubersnap.challange.todo.app.util.valueOrZero
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TodoModificationViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {

    private var savedTodoId by mutableStateOf<Long?>(null)

    private var savedTodo by mutableStateOf<Todo?>(null)

    var uiMode by mutableStateOf(UiMode.CREATE)
        private set

    var title by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    var dueDate by mutableStateOf(0L)
        private set

    /**
     * Flag for indicate modification action is enabled on the screen
     */
    var actionEnabled by mutableStateOf(false)
        private set

    fun setupTodoId(todoId: Long? = null) {
        if (todoId != null && todoId != 0L) {
            savedTodoId = todoId
            uiMode = UiMode.UPDATE
            getTodo()
        } else {
            actionEnabled = true
        }
    }

    fun onTitleUpdate(newTitle: String) {
        title = newTitle
        compareDiff()
    }

    fun onDescriptionUpdate(newDescription: String) {
        description = newDescription
        compareDiff()
    }

    fun onDueDateUpdate(newDueDate: Long) {
        dueDate = newDueDate
        compareDiff()
    }

    fun onDeleteAction() {
        deleteTodo()
    }

    fun onSaveAction() {
        when (uiMode) {
            UiMode.CREATE -> createTodo()
            UiMode.UPDATE -> updateTodo()
        }
    }

    // Load state for indicate every action loading only
    var actionLoadState by mutableStateOf<LoadState<ModificationState>?>(null)

    fun onClearActionLoadState() {
        actionLoadState = null
    }

    private fun getTodo() {
        Timber.i("onGetTodo")
        viewModelScope.launch(Dispatchers.IO) {
            actionLoadState = LoadState.Loading()
            repository.getTodo(savedTodoId.valueOrZero()).let { resource ->
                when (resource) {
                    is Resource.Success -> {
                        actionLoadState = LoadState.Available(ModificationState.VIEW)
                        resource.data?.let { todo ->
                            savedTodo = todo
                            title = todo.title
                            description = todo.description
                            dueDate = todo.dueDate
                        }
                    }
                    is Resource.Failed -> {
                        actionLoadState = LoadState.Failed()
                    }
                }
            }
        }
    }

    private fun createTodo() {
        Timber.i("onCreateTodo")
        viewModelScope.launch(Dispatchers.IO) {
            actionLoadState = LoadState.Loading()
            repository.createTodo(
               todo = requireCurrentTodo()
            ).let { resource ->
                actionLoadState = when (resource) {
                    is Resource.Success -> LoadState.Available(ModificationState.CREATE)
                    is Resource.Failed -> LoadState.Failed()
                }
            }
        }
    }

    private fun updateTodo() {
        Timber.i("onUpdateTodo")
        viewModelScope.launch(Dispatchers.IO) {
            actionLoadState = LoadState.Loading()
            repository.updateTodo(
                todo = requireCurrentTodo()
            ).let { resource ->
                actionLoadState = when (resource) {
                    is Resource.Success -> LoadState.Available(ModificationState.UPDATE)
                    is Resource.Failed -> LoadState.Failed()
                }
            }
        }
    }

    private fun deleteTodo() {
        Timber.i("onDeleteTodo")
        viewModelScope.launch(Dispatchers.IO) {
            actionLoadState = LoadState.Loading()
            repository.deleteTodo(
                todo = requireCurrentTodo()
            ).let { resource ->
                actionLoadState = when (resource) {
                    is Resource.Success -> LoadState.Available(ModificationState.DELETE)
                    is Resource.Failed -> LoadState.Failed()
                }
            }
        }
    }

    private fun requireCurrentTodo() =  Todo(
        id = savedTodoId.valueOrZero(),
        title = title,
        description = description,
        dueDate = dueDate
    )

    private fun compareDiff() {
        savedTodo?.let { todo ->
            viewModelScope.launch(Dispatchers.Default) {
                actionEnabled = todo.title != title || todo.description != description || todo.dueDate != dueDate
            }
        }
    }

}