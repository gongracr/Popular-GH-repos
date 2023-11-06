package com.gongracr.ghreposloader.ui.cards

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.gongracr.core.domain.model.GHProject

@Composable
fun DismissibleGHProjectCardView(
    ghProject: GHProject,
    onProjectHidden: (GHProject) -> Unit,
    onProjectClicked: (GHProject) -> Unit
) {
    var isVisible by remember { mutableStateOf(true) }
    val fadeInTransition = 900
    val fadeOutTransition = 500
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(
            initialOffsetY = { -it },
            animationSpec = TweenSpec(durationMillis = fadeInTransition)
        ) + fadeIn(animationSpec = TweenSpec(durationMillis = fadeInTransition)),
        exit = fadeOut(animationSpec = TweenSpec(durationMillis = fadeOutTransition))
    ) {
        CompactProjectCardView(
            project = ghProject,
            onHideProjectClick = {
                isVisible = false
                onProjectHidden(ghProject)
            },
            onProjectClick = {
                onProjectClicked(ghProject)
            }
        )
    }
}