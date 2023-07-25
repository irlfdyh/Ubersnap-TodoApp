package com.ubersnap.challange.todo.app.ui.feature.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ubersnap.challange.todo.app.R
import com.ubersnap.challange.todo.app.entity.Todo
import com.ubersnap.challange.todo.app.ui.LoadState
import com.ubersnap.challange.todo.app.ui.component.EmptyScreen
import com.ubersnap.challange.todo.app.ui.component.ErrorScreen
import com.ubersnap.challange.todo.app.ui.component.ItemTodo
import com.ubersnap.challange.todo.app.ui.component.LoadingScreen

@Composable
fun TodoListScreen(
    viewModel: TodoListViewModel = hiltViewModel(),
    onTodoItemClick: (Long) -> Unit = { },
    onCreateTodoClick: () -> Unit = { }
) {
    LaunchedEffect(key1 = Unit, block = {
        viewModel.getTodos()
    })
    TodoListScreenUi(
        todoLoadState = viewModel.todoLoadState,
        onTodoItemClick = onTodoItemClick,
        onCreateTodoClick = onCreateTodoClick,
        onReloadAction = viewModel::onReloadAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TodoListScreenUi(
    todoLoadState: LoadState<List<Todo>>,
    onTodoItemClick: (Long) -> Unit = { },
    onCreateTodoClick: () -> Unit = { },
    onReloadAction: () -> Unit = { }
) {

    val listState = rememberLazyListState()
    val showFab by remember {
        derivedStateOf {
            !listState.isScrollInProgress
        }
    }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = showFab,
                enter = slideInVertically { height -> height } + fadeIn(),
                exit = slideOutVertically { height -> height } + fadeOut()
            ) {
                FloatingActionButton(
                    onClick = onCreateTodoClick
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = stringResource(id = R.string.cd_add_todo)
                    )
                }
            }
        }
    ) { innerPadding ->
        when (todoLoadState) {
            is LoadState.Available -> {
                AvailableScreen(
                    modifier = Modifier.padding(innerPadding),
                    listState = listState,
                    todos = todoLoadState.data,
                    onTodoItemClick = onTodoItemClick
                )
            }
            is LoadState.Empty -> {
                EmptyScreen(
                    modifier = Modifier.padding(innerPadding)
                )
            }
            is LoadState.Failed -> {
                ErrorScreen(
                    modifier = Modifier.padding(innerPadding),
                    onRetryAction = onReloadAction
                )
            }
            is LoadState.Loading -> {
                LoadingScreen(
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }

}

@Composable
private fun AvailableScreen(
    modifier: Modifier = Modifier,
    listState: LazyListState,
    todos: List<Todo>,
    onTodoItemClick: (Long) -> Unit = { }
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        state = listState,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = todos,
            key = { it.id }
        ) { todo ->
            ItemTodo(
                todo = todo,
                onItemClick = { onTodoItemClick(todo.id) }
            )
        }
    }
}