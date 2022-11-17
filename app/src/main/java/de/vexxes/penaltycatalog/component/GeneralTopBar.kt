package de.vexxes.penaltycatalog.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
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
    onFilterListClicked: () -> Unit,
    onTextChanged: (String) -> Unit,
    onCloseClicked: () -> Unit
) {
    when(searchAppBarState) {
        SearchAppBarState.CLOSED -> {
            DefaultTopBar(
                onSearchClicked = onDefaultSearchClicked,
                onFilterListClicked = onFilterListClicked
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
    onFilterListClicked: () -> Unit
) {
    TopAppBar(
        title = { },

        actions = {
            IconButton(
                onClick = {
                    onSearchClicked()
                }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.SearchTopBar)
                )
            }
            IconButton(
                onClick = {
                    onFilterListClicked()
                }) {
                Icon(
                    imageVector = Icons.Default.FilterList,
                    contentDescription = stringResource(id = R.string.FilterListTopBar)
                )
            }
        }
    )
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

@Preview(showBackground = true)
@Composable
fun DefaultTopBarPreview(

) {
    DefaultTopBar(
        onSearchClicked = { },
        onFilterListClicked = { }
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