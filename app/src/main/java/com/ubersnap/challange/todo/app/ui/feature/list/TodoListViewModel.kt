package com.ubersnap.challange.todo.app.ui.feature.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ubersnap.challange.todo.app.data.Resource
import com.ubersnap.challange.todo.app.data.repository.TodoRepository
import com.ubersnap.challange.todo.app.entity.Todo
import com.ubersnap.challange.todo.app.ui.LoadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val todoRepository: TodoRepository
) : ViewModel() {

    var todoLoadState by mutableStateOf<LoadState<List<Todo>>>(LoadState.Loading())
        private set

    fun getTodos(retry: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) {
            todoRepository
                .getTodos()
                .distinctUntilChanged()
                .transform { todos ->
                    if (todos.isNotEmpty()) {
                        emit(LoadState.Available(todos))
                    } else {
                        emit(LoadState.Empty())
                    }
                }
                .onStart {
                    // Emit loading value if this triggered by retry action
                    if (retry) {
                        emit(LoadState.Loading())
                    }
                }
                .onEmpty { emit(LoadState.Empty()) }
                .catch { emit(LoadState.Failed()) }
                .collectLatest {
                    todoLoadState = it
                }
        }
    }

    fun onReloadAction() {
        getTodos(retry = true)
    }

    var deleteLoadState by mutableStateOf<LoadState<String>?>(null)

    fun onDeleteItem(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteLoadState = LoadState.Loading()
            todoRepository.deleteTodo(todo).let { resource ->
                deleteLoadState = when (resource) {
                    is Resource.Success -> LoadState.Empty()
                    is Resource.Failed -> LoadState.Failed()
                }
            }
        }
    }

    fun onResetLoadState() {
        deleteLoadState = null
    }

}