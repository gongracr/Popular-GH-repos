package com.gongracr.ghreposloader.ui.search

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.gongracr.ghreposloader.R
import com.gongracr.ghreposloader.ui.theme.colors
import com.gongracr.ghreposloader.ui.theme.dimensions

@ExperimentalComposeUiApi
@Composable
fun SearchTopBar(initialSearch: String = "", onSearchQueryChanged: (String) -> Unit, onCloseSearchClicked: () -> Unit) {

    var searchText by remember { mutableStateOf(initialSearch) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val textFieldFocus = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        TextField(
            value = searchText,
            onValueChange = {
                searchText = it
                onSearchQueryChanged(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .focusRequester(focusRequester = textFieldFocus),
            placeholder = {
                Text(text = stringResource(R.string.search_projects_hint), minLines = 1, maxLines = 1, color = colors().onSurface)
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    modifier = Modifier.size(dimensions().topBarIconSize),
                    tint = colors().onSurface
                )
            },
            trailingIcon = {
                IconButton(onClick = {
                    searchText = ""
                    onCloseSearchClicked()
                    keyboardController?.hide()
                    textFieldFocus.freeFocus()
                    focusManager.clearFocus()
                }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = null,
                        modifier = Modifier.size(dimensions().topBarIconSize),
                        tint = colors().onSurface
                    )
                }
            }
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun PreviewSearchBarActive() {
    SearchTopBar(
        initialSearch = "Some search",
        onSearchQueryChanged = {},
        onCloseSearchClicked = { },
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun PreviewSearchBarInactive() {
    SearchTopBar(
        onSearchQueryChanged = {},
        onCloseSearchClicked = {},
    )
}