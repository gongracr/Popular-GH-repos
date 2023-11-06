package com.gongracr.ghreposloader.ui.cards

import android.icu.text.NumberFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gongracr.core.domain.model.GHProject
import com.gongracr.ghreposloader.R
import com.gongracr.ghreposloader.ui.theme.colors
import com.gongracr.ghreposloader.ui.theme.dimensions

@Composable
fun CompactProjectCardView(project: GHProject, onHideProjectClick: () -> Unit = {}, onProjectClick: () -> Unit = {}) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensions().spacing16x)
            .height(dimensions().detailedProjectCardImageSize)
            .clickable { onProjectClick() },
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensions().spacing16x),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Display project's owner avatar picture
            AsyncImage(
                model = project.avatarUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(dimensions().compactProjectCardImageSize)
                    .clip(CircleShape)
                    .background(colors().primaryContainer)
            )

            Spacer(modifier = Modifier.width(dimensions().spacing16x))

            // Display project's name and description
            Column {
                Text(
                    text = project.name,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = project.description ?: stringResource(id = R.string.empty_description_fallback),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    overflow = Ellipsis,
                    modifier = Modifier.heightIn(0.dp, dimensions().spacing64x)
                )
                Spacer(modifier = Modifier.height(dimensions().spacing16x))
                Text(
                    text = NumberFormat.getNumberInstance()
                        .format(project.stargazersCount) + " " + stringResource(id = R.string.project_card_stars),
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(dimensions().spacing16x))
                Button(onClick = onHideProjectClick) {
                    Text(text = stringResource(id = R.string.project_card_hide_title_button))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CompactProjectCardViewPreview() {
    CompactProjectCardView(
        project = GHProject(
            10,
            "Awesome Project",
            "Awesome Company/Awesome Project",
            "This is an awesome project",
            98000,
            "Kotlin",
            9999128,
            "Awesome Company",
            "some-url.com",
            "some-picture-url.com"
        )
    )
}


