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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import de.vexxes.penaltycatalog.R
import de.vexxes.penaltycatalog.domain.enums.SortOrder
import de.vexxes.penaltycatalog.util.SearchAppBarState
import kotlinx.coroutines.delay

@Composable
fun GeneralTopBar(
    searchAppBarState: SearchAppBarState,
    searchTextState: String,
    onDefaultSearchClicked: () -> Unit,
    onSortClicked: (SortOrder) -> Unit,
    onSearchTextChanged: (String) -> Unit,
    onCloseClicked: () -> Unit
) {

    when(searchAppBarState) {
        SearchAppBarState.CLOSED -> {
            DefaultTopBar(
                onSearchClicked = onDefaultSearchClicked,
                onSortClicked = onSortClicked
            )
        }

        else -> {
            SearchTopBar(
                text = searchTextState,
                onSearchTextChanged = onSearchTextChanged,
                onCloseClicked = onCloseClicked
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTopBar(
    onSearchClicked: () -> Unit,
    onSortClicked: (SortOrder) -> Unit
) {
    TopAppBar(
        title = { },

        actions = {
            SearchIcon(onSearchClicked = onSearchClicked)
            SortIcon(onSortClicked = onSortClicked)
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
    onSortClicked: (SortOrder) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    SortDropDownMenu(
        expanded = expanded,
        onDismiss = {
            expanded = false
        },
        onSortClicked = { sortOrder ->
            expanded = false
            onSortClicked(sortOrder)
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchTopBar(
    text: String,
    onSearchTextChanged: (String) -> Unit,
    onCloseClicked: () -> Unit
) {
    val showKeyboard by remember { mutableStateOf(true) }
    val focusRequester = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        value = text,
        onValueChange = { onSearchTextChanged(it) },
        placeholder = {
            Text(
                text = stringResource(id = R.string.SearchPlaceholder)
            )
        },
        singleLine = true,
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

    // LaunchedEffect prevents endless focus request
    LaunchedEffect(focusRequester) {
        if(showKeyboard) {
            focusRequester.requestFocus()
            delay(100)
            keyboard?.show()
        }
    }
}

@Composable
private fun SortDropDownMenu(
    expanded: Boolean,
    onDismiss: () -> Unit,
    onSortClicked: (SortOrder) -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = {
            onDismiss()
        }
    ) {
        SortOrder.values().forEach { sortOrder ->
            DropdownMenuItem(
                onClick = { onSortClicked(sortOrder) },
                leadingIcon = { Icon(imageVector = sortOrder.imageVector, contentDescription = "")},
                text = { Text(text = sortOrder.name) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultTopBarPreview(

) {
    DefaultTopBar(
        onSearchClicked = { },
        onSortClicked = { }
    )
}

@Preview(showBackground = true)
@Composable
fun SearchTopBarPreview(

) {
    SearchTopBar(
        text = "",
        onSearchTextChanged = { },
        onCloseClicked = { }
    )
}

@Preview(showBackground = true)
@Composable
fun SortDropDownMenuPreview() {
    SortDropDownMenu(
        expanded = true,
        onDismiss = { },
        onSortClicked = { }
    )
}