package com.gongracr.ghreposloader.ui.cards

import android.icu.text.NumberFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gongracr.core.domain.model.GHProject
import com.gongracr.ghreposloader.R
import com.gongracr.ghreposloader.ui.theme.dimensions
import com.gongracr.ghreposloader.ui.utils.Utils.TEST_GH_PROJECT

@Composable
fun DetailedGHProjectCardView(project: GHProject) {
    val uriHandler = LocalUriHandler.current
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(dimensions().detailedProjectCardPadding),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensions().detailedProjectCardPadding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .padding(top = dimensions().spacing16x),
            ) {
                AsyncImage(
                    model = project.avatarUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(dimensions().detailedProjectCardImageSize)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.secondary),
                )
            }
            Spacer(modifier = Modifier.size(dimensions().spacing40x))
            Text(
                text = project.name.capitalize(Locale.current),
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.size(dimensions().spacing16x))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Icon(
                    modifier = Modifier
                        .wrapContentHeight()
                        .size(dimensions().spacing32x),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_github_logo),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.size(dimensions().spacing16x))
                Text(
                    text = project.fullname,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
            Spacer(modifier = Modifier.size(dimensions().spacing16x))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Icon(
                    modifier = Modifier
                        .wrapContentHeight()
                        .size(dimensions().spacing32x),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_star),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.size(dimensions().spacing16x))
                Text(
                    text = NumberFormat.getNumberInstance().format(project.stargazersCount),
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
            Spacer(modifier = Modifier.size(dimensions().spacing16x))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Icon(
                    modifier = Modifier
                        .wrapContentHeight()
                        .size(dimensions().spacing32x),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_register),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.size(dimensions().spacing16x))
                Text(
                    text = project.description ?: stringResource(R.string.empty_description_fallback),
                    style = MaterialTheme.typography.bodyLarge,
                    overflow = Ellipsis,
                    modifier = Modifier.heightIn(0.dp, dimensions().spacing200x)
                )
            }
            Row(modifier = Modifier.weight(1f)){
                Button(
                    onClick = { uriHandler.openUri(project.htmlUrl) },
                    modifier = Modifier
                        .alignByBaseline()
                        .fillMaxWidth()
                        .padding(top = dimensions().spacing16x)
                ) {
                    Text(
                        text = stringResource(R.string.open_in_browser_button_text),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewDetailedProjectCardView() {
    DetailedGHProjectCardView(TEST_GH_PROJECT)
}