package de.vexxes.penaltycatalog.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import de.vexxes.penaltycatalog.R
import de.vexxes.penaltycatalog.domain.enums.SearchAppBarState
import de.vexxes.penaltycatalog.ui.theme.PenaltyCatalogTheme
import kotlinx.coroutines.delay

@Composable
fun GeneralTopBar(
    searchAppBarState: SearchAppBarState,
    searchTextState: String,
    onDefaultSearchClicked: () -> Unit,
    onSearchTextChanged: (String) -> Unit,
    onCloseClicked: () -> Unit,
    sortIcon: @Composable () -> Unit = {},
) {

    when(searchAppBarState) {
        SearchAppBarState.CLOSED -> {
            DefaultTopBar(
                onSearchClicked = onDefaultSearchClicked,
                sortIcon = sortIcon
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
private fun DefaultTopBar(
    onSearchClicked: () -> Unit,
    sortIcon: @Composable () -> Unit = {}
) {
    TopAppBar(
        title = { },

        actions = {
            SearchIcon(onSearchClicked = onSearchClicked)
            sortIcon()
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
private fun DummySortIcon() {
    IconButton(
        onClick = { }
    ) {
        Icon(
            imageVector = Icons.Default.Sort,
            contentDescription = stringResource(id = R.string.SortTopBar)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
private fun SearchTopBar(
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

@Preview(name = "Light Theme", showBackground = true)
@Preview(name = "Dark Theme", showBackground = true, uiMode = UI_MODE_NIGHT_YES)@Composable
fun DefaultTopBarPreview() {
    PenaltyCatalogTheme {
        DefaultTopBar(
            onSearchClicked = { },
            sortIcon = { }
        )
    }
}

@Preview(name = "Light Theme", showBackground = true)
@Preview(name = "Dark Theme", showBackground = true, uiMode = UI_MODE_NIGHT_YES)@Composable
fun SearchAndSortTopBarPreview() {
    PenaltyCatalogTheme {
        DefaultTopBar(
            onSearchClicked = { },
            sortIcon = { DummySortIcon() }
        )
    }
}

@Preview(name = "Light Theme", showBackground = true)
@Preview(name = "Dark Theme", showBackground = true, uiMode = UI_MODE_NIGHT_YES)@Composable
fun SearchTopBarPreview() {
    PenaltyCatalogTheme {
        SearchTopBar(
            text = "",
            onSearchTextChanged = { },
            onCloseClicked = { }
        )
    }
}