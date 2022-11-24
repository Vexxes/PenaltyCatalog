package de.vexxes.penaltycatalog.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
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
fun BackSaveTopBar(
    onBackClicked: () -> Unit,
    onSaveClicked: () -> Unit
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
            IconButton(
                onClick = {
                    onSaveClicked()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = stringResource(id = R.string.Save)
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun BackSaveTopBarPreview() {
    BackSaveTopBar(
        onBackClicked = { },
        onSaveClicked = { }
    )
}