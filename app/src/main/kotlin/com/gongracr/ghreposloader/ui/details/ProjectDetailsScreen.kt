package com.gongracr.ghreposloader.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.gongracr.core.domain.model.GHProject
import com.gongracr.ghreposloader.R
import com.gongracr.ghreposloader.presentation.details.ProjectDetailsViewModel
import com.gongracr.ghreposloader.ui.appbar.TopAppBar
import com.gongracr.ghreposloader.ui.cards.DetailedGHProjectCardView
import com.gongracr.ghreposloader.ui.theme.colors
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
fun ProjectDetailsScreen(id: String, projectDetailsViewModel: ProjectDetailsViewModel = hiltViewModel()) {

    LaunchedEffect(projectDetailsViewModel) {
        projectDetailsViewModel.loadProjectDetails(id)
    }

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = stringResource(R.string.screen_detailed_project_title),
            )
        },
        modifier = Modifier.background(colors().background),
    ) { internalPadding ->
        ProjectDetailsContent(internalPadding, projectDetailsViewModel.projectState.project)
    }
}

@Composable
fun ProjectDetailsContent(internalPadding: PaddingValues, project: GHProject?) {
    Box(modifier = Modifier.padding(internalPadding)) {
        project?.let { project ->
            DetailedGHProjectCardView(project)
        }
    }
}
