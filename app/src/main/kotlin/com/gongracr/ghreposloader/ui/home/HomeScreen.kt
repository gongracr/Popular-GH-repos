package com.gongracr.ghreposloader.ui.home

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.gongracr.core.domain.model.GHProject
import com.gongracr.ghreposloader.R
import com.gongracr.ghreposloader.presentation.home.HomeViewModel
import com.gongracr.ghreposloader.presentation.navigation.BackStackMode
import com.gongracr.ghreposloader.presentation.navigation.NavigationCommand
import com.gongracr.ghreposloader.presentation.navigation.Navigator
import com.gongracr.ghreposloader.ui.appbar.TopAppBar
import com.gongracr.ghreposloader.ui.cards.DismissibleGHProjectCardView
import com.gongracr.ghreposloader.ui.destinations.ProjectDetailsScreenDestination
import com.gongracr.ghreposloader.ui.search.SearchTopBar
import com.gongracr.ghreposloader.ui.theme.colors
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(navigator: Navigator, homeViewModel: HomeViewModel = hiltViewModel()) {

    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val ghProjectsPagingItems = homeViewModel.ghProjectsState.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        homeViewModel.infoMessage.collect {
            snackbarHostState.showSnackbar(
                message = context.resources.getString(it.resId, *it.formatArgs),
                withDismissAction = true
            )
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = stringResource(R.string.screen_home_title),
            )
        },
        modifier = Modifier.background(colors().background),
    ) { internalPadding ->
        Column(
            modifier = Modifier
                .padding(internalPadding)
                .wrapContentHeight()
        ) {
            SearchTopBar(
                onSearchQueryChanged = homeViewModel::onSearchQueryChanged,
                onCloseSearchClicked = homeViewModel::onSearchCancelled
            )
            GHProjectsListContent(
                ghProjectsPagingItems = ghProjectsPagingItems,
                onProjectClicked = {
                    navigator.navigate(
                        NavigationCommand(
                            destination = ProjectDetailsScreenDestination(it.id.toString()),
                            backStackMode = BackStackMode.UPDATE_EXISTING
                        )
                    )
                },
                onProjectHidden = homeViewModel::hideProject
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GHProjectsListContent(
    ghProjectsPagingItems: LazyPagingItems<GHProject>,
    onProjectClicked: (GHProject) -> Unit,
    onProjectHidden: (GHProject) -> Unit
) {
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val animationDuration = 500

    LazyColumn(state = listState) {
        items(ghProjectsPagingItems.itemCount, key = { index ->
            // We use the project's ID as the key for the item to ensure the list recomposes correctly after removing an item
            ghProjectsPagingItems[index]?.id ?: index
        }) { index ->
            val itemVisibility = remember { Animatable(1f) }

            ghProjectsPagingItems[index]?.let { ghProject ->

                Box(modifier = Modifier.animateItemPlacement()) {
                    DismissibleGHProjectCardView(ghProject = ghProject,
                        onProjectHidden = {
                            scope.launch {
                                itemVisibility.animateTo(
                                    targetValue = 0f,
                                    animationSpec = tween(animationDuration)
                                )
                                onProjectHidden(it)
                            }
                        }, onProjectClicked = {
                            onProjectClicked(it)
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewHomeScreen() {
    HomeScreen(navigator = Navigator({}, NavHostController(LocalContext.current)))
}
