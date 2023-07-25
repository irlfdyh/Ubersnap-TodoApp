package com.ubersnap.challange.todo.app.ui.feature.modification

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ubersnap.challange.todo.app.R
import com.ubersnap.challange.todo.app.ui.LoadState
import com.ubersnap.challange.todo.app.ui.component.LoadingDialog
import com.ubersnap.challange.todo.app.ui.component.MessageDialog
import com.ubersnap.challange.todo.app.util.asDateFormat

@Composable
fun TodoModificationScreen(
    viewModel: TodoModificationViewModel = hiltViewModel(),
    todoId: Long? = null,
    onNavigateUp: () -> Unit = { }
) {
    LaunchedEffect(key1 = Unit, block = {
        viewModel.setupTodoId(todoId)
    })
    TodoModificationScreenUi(
        uiMode = viewModel.uiMode,
        onNavigateUp = onNavigateUp,
        onDeleteAction = viewModel::onDeleteAction,
        onSaveAction = viewModel::onSaveAction,
        title = viewModel.title,
        onTitleUpdate = viewModel::onTitleUpdate,
        description = viewModel.description,
        onDescriptionUpdate = viewModel::onDescriptionUpdate,
        dueDate = viewModel.dueDate,
        onDueDateUpdate = viewModel::onDueDateUpdate,
        enableButton = viewModel.actionEnabled,
        actionLoadState = viewModel.actionLoadState,
        onClearActionLoadState = viewModel::onClearActionLoadState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TodoModificationScreenUi(
    uiMode: UiMode,
    onNavigateUp: () -> Unit = { },
    onDeleteAction: () -> Unit = { },
    onSaveAction: () -> Unit = { },
    title: String,
    onTitleUpdate: (String) -> Unit = { },
    description: String,
    onDescriptionUpdate: (String) -> Unit = { },
    dueDate: Long,
    onDueDateUpdate: (Long) -> Unit = { },
    enableButton: Boolean,
    actionLoadState: LoadState<ModificationState>?,
    onClearActionLoadState: () -> Unit = { }
) {

    var showMessageDialog by remember { mutableStateOf("") }
    var showLoadingDialog by remember { mutableStateOf(false) }

    when (actionLoadState) {
        is LoadState.Available -> {
            showLoadingDialog = false
            when (actionLoadState.data) {
                ModificationState.CREATE -> {
                    showMessageDialog = stringResource(id = R.string.msg_todo_created)
                }
                ModificationState.UPDATE -> {
                    showMessageDialog = stringResource(id = R.string.msg_todo_updated)
                }
                ModificationState.DELETE -> {
                    showMessageDialog = stringResource(id = R.string.msg_todo_deleted)
                }
                ModificationState.VIEW -> Unit
            }
        }
        is LoadState.Empty -> Unit
        is LoadState.Failed -> Unit
        is LoadState.Loading -> {
            showLoadingDialog = true
        }
        else -> Unit
    }

    if (showMessageDialog != "") {
        MessageDialog(
            text = showMessageDialog,
            onDismiss = {
                showMessageDialog = ""
                onClearActionLoadState()
                onNavigateUp()
            }
        )
    }

    if (showLoadingDialog) {
        LoadingDialog()
    }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .windowInsetsPadding(
                WindowInsets.systemBars.only(WindowInsetsSides.Horizontal)
            ),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (uiMode == UiMode.UPDATE) {
                            stringResource(id = R.string.str_update_data)
                        } else {
                            stringResource(id = R.string.str_create_data)
                        }
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = stringResource(id = R.string.cd_navigate_up)
                        )
                    }
                },
                actions = {
                    if (uiMode == UiMode.UPDATE) {
                        IconButton(onClick = onDeleteAction) {
                            Icon(
                                imageVector = Icons.Rounded.Delete,
                                contentDescription = stringResource(id = R.string.cd_delete_todo)
                            )
                        }
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->

        val interactionSource = remember { MutableInteractionSource() }
        val isPressed by interactionSource.collectIsPressedAsState()
        var showDatePicker by remember { mutableStateOf(false) }
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = System.currentTimeMillis()
        )
        if (isPressed) {
           showDatePicker = true
        }
        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    Row {
                        TextButton(
                            onClick = {
                                showDatePicker = false
                            }
                        ) {
                            Text(text = stringResource(id = R.string.str_cancel))
                        }
                        TextButton(
                            onClick = {
                                onDueDateUpdate(datePickerState.selectedDateMillis ?: 0L)
                                showDatePicker = false
                            }
                        ) {
                            Text(text = stringResource(id = R.string.str_ok))
                        }
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = onTitleUpdate,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = stringResource(id = R.string.str_title))
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                singleLine = true
            )
            OutlinedTextField(
                value = description,
                onValueChange = onDescriptionUpdate,
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(text = stringResource(id = R.string.str_description))
                },
                placeholder = {
                    Text(text = stringResource(id = R.string.str_description))
                },
            )
            OutlinedTextField(
                value = if (dueDate != 0L) dueDate.asDateFormat() else "",
                onValueChange = { },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                label = {
                    Text(text = stringResource(id = R.string.str_due_date))
                },
                placeholder = {
                    Text(text = stringResource(id = R.string.str_due_date))
                },
                trailingIcon = {
                    IconButton(onClick = { showDatePicker = true }) {
                        Icon(
                            imageVector = Icons.Rounded.CalendarMonth,
                            contentDescription = stringResource(id = R.string.cd_pick_date)
                        )
                    }
                },
                interactionSource = interactionSource
            )
            Button(
                onClick = onSaveAction,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                enabled = enableButton
            ) {
                Text(text = stringResource(id = R.string.str_save))
            }
        }
    }
}

