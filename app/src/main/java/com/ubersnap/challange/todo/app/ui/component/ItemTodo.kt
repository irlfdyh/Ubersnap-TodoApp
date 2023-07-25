package com.ubersnap.challange.todo.app.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ubersnap.challange.todo.app.R
import com.ubersnap.challange.todo.app.data.source.local.TodoExampleData
import com.ubersnap.challange.todo.app.entity.Todo
import com.ubersnap.challange.todo.app.util.asDateFormat

@Composable
fun ItemTodo(
    todo: Todo,
    modifier: Modifier = Modifier,
    onItemClick: () -> Unit = { },
    onDeleteItem: () -> Unit = { }
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .clickable { onItemClick() }
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ){
                Text(
                    text = todo.title,
                    modifier = Modifier.fillMaxWidth(),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = todo.description,
                    modifier = Modifier.fillMaxWidth(),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    style = MaterialTheme.typography.bodySmall
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.str_due_date),
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(
                        text = todo.dueDate.asDateFormat(),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            IconButton(onClick = onDeleteItem) {
                Icon(
                    imageVector = Icons.Rounded.Delete, 
                    contentDescription = stringResource(id = R.string.cd_delete_todo)
                )
            }
        }
    }
}

@Preview
@Composable
private fun ItemTodoPreview() {
    ItemTodo(todo = TodoExampleData.todos.first().asTodo())
}