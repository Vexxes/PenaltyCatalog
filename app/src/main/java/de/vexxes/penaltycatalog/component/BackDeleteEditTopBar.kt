package de.vexxes.penaltycatalog.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import de.vexxes.penaltycatalog.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackDeleteEditTopBar(
    onBackClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
    onEditClicked: () -> Unit
) {
    TopAppBar(
        title = { },

        navigationIcon = {
            IconButton(
                onClick = {
                    onBackClicked()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.BackArrow)
                )
            }
        },

        actions = {
            DeleteAction(onDeleteClicked = onDeleteClicked)
            EditAction(onEditClicked = onEditClicked)
        }
    )
}

@Composable
private fun DeleteAction(
    onDeleteClicked: () -> Unit
) {
    IconButton(
        onClick = {
            onDeleteClicked()
        }
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = stringResource(id = R.string.Delete)
        )
    }
}

@Composable
private fun EditAction(
    onEditClicked: () -> Unit
) {
    IconButton(
        onClick = {
            onEditClicked()
        }
    ) {
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = stringResource(id = R.string.Edit)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BackEditTopBarPreview() {
    BackDeleteEditTopBar(
        onBackClicked = { },
        onDeleteClicked = { },
        onEditClicked = { }
    )
}