package de.vexxes.penaltycatalog.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import de.vexxes.penaltycatalog.R
import de.vexxes.penaltycatalog.util.SearchAppBarState

@Composable
fun GeneralTopBar(
    searchAppBarState: SearchAppBarState,
    searchTextState: String,
    onDefaultSearchClicked: () -> Unit,
    onSearchBarClicked: () -> Unit,
    onAscendingClicked: () -> Unit,
    onDescendingClicked: () -> Unit,
    onTextChanged: (String) -> Unit,
    onCloseClicked: () -> Unit
) {

    when(searchAppBarState) {
        SearchAppBarState.CLOSED -> {
            DefaultTopBar(
                onSearchClicked = onDefaultSearchClicked,
                onAscendingClicked = onAscendingClicked,
                onDescendingClicked = onDescendingClicked
            )
        }

        else -> {
            SearchTopBar(
                text = searchTextState,
                onTextChanged = onTextChanged,
                onCloseClicked = onCloseClicked,
                onSearchClicked = onSearchBarClicked
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTopBar(
    onSearchClicked: () -> Unit,
    onAscendingClicked: () -> Unit,
    onDescendingClicked: () -> Unit
) {
    TopAppBar(
        title = { },

        actions = {
            SearchIcon(onSearchClicked = onSearchClicked)
            SortIcon(
                onAscendingClicked = onAscendingClicked,
                onDescendingClicked = onDescendingClicked
            )
        }
    )
}

@Composable
private fun SearchIcon(
    onSearchClicked: () -> Unit
) {
    IconButton(
        onClick = {
            onSearchClicked()
        }) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = stringResource(id = R.string.SearchTopBar)
        )
    }
}

@Composable
private fun SortIcon(
    onAscendingClicked: () -> Unit,
    onDescendingClicked: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    SortDropDownMenu(
        expanded = expanded,
        onDismiss = {
            expanded = false
        },
        onAscendingClicked = {
            expanded = false
            onAscendingClicked()
        },
        onDescendingClicked = {
            expanded = false
            onDescendingClicked()
        }
    )

    IconButton(
        onClick = {
            expanded = true
        }) {
        Icon(
            imageVector = Icons.Default.FilterList,
            contentDescription = stringResource(id = R.string.FilterListTopBar)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopBar(
    text: String,
    onTextChanged: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: () -> Unit
) {
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        onValueChange = { onTextChanged(it) },
        placeholder = {
            Text(
                text = stringResource(id = R.string.SearchPlaceholder)
            )
        },
        singleLine = true,
        leadingIcon = {
            IconButton(
                onClick = { onSearchClicked() }
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.SearchTopBar)
                )
            }
        },
        trailingIcon = {
            IconButton(
                onClick = { onCloseClicked() }
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(id = R.string.CloseTopBar)
                )
            }
        }

    )
}

@Composable
private fun SortDropDownMenu(
    expanded: Boolean,
    onDismiss: () -> Unit,
    onAscendingClicked: () -> Unit,
    onDescendingClicked: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = {
            onDismiss()
        }
    ) {
        DropdownMenuItem(
            onClick = { onAscendingClicked() },
            text = { Text(text = stringResource(id = R.string.Ascending)) }
        )

        DropdownMenuItem(
            onClick = { onDescendingClicked() },
            text = { Text(text = stringResource(id = R.string.Descending)) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultTopBarPreview(

) {
    DefaultTopBar(
        onSearchClicked = { },
        onAscendingClicked = { },
        onDescendingClicked = { }
    )
}

@Preview(showBackground = true)
@Composable
fun SearchTopBarPreview(

) {
    SearchTopBar(
        text = "",
        onTextChanged = { },
        onCloseClicked = { },
        onSearchClicked = { }
    )
}

@Preview(showBackground = true)
@Composable
fun SortDropDownMenuPreview() {
    SortDropDownMenu(
        expanded = true,
        onDismiss = { },
        onAscendingClicked = { },
        onDescendingClicked = { }
    )
}