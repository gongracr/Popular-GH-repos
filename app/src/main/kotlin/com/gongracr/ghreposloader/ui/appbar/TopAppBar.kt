package com.gongracr.ghreposloader.ui.appbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.gongracr.ghreposloader.R
import com.gongracr.ghreposloader.ui.theme.colors
import com.gongracr.ghreposloader.ui.theme.dimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    title: String,
    icon: ImageVector = ImageVector.vectorResource(R.drawable.ic_github_logo),
) {

    Row(verticalAlignment = Alignment.CenterVertically) {
        TopAppBar(
            modifier = Modifier
                .height(dimensions().normalTopBarHeight)
                .wrapContentHeight(),
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = colors().onPrimary,
                    modifier = Modifier.padding(
                        start = dimensions().spacing20x,
                        end = dimensions().spacing6x
                    )
                )
            },
            navigationIcon = {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(start = dimensions().spacing6x)
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = dimensions().topBarIconPadding)
                            .size(dimensions().topBarIconSize),
                        tint = colors().onPrimary
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = colors().primary, titleContentColor = colors().primary),
        )
    }
}

@Preview
@Composable
fun RandomTopAppBarPreview() {
    TopAppBar(title = "GH Repos Loader",)
}