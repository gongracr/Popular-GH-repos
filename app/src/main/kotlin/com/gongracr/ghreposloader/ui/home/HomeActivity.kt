package com.gongracr.ghreposloader.ui.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.gongracr.ghreposloader.presentation.navigation.NavigationCommand
import com.gongracr.ghreposloader.presentation.navigation.NavigationGraph
import com.gongracr.ghreposloader.presentation.navigation.navigateToItem
import com.gongracr.ghreposloader.presentation.navigation.rememberNavigator
import com.gongracr.ghreposloader.ui.destinations.HomeScreenDestination
import com.gongracr.ghreposloader.ui.theme.MyTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {

    private val navigationCommands: MutableSharedFlow<NavigationCommand> = MutableSharedFlow()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navigator = rememberNavigator(this@HomeActivity::finish)
                    val scope = rememberCoroutineScope()
                    NavigationGraph(
                        navigator = navigator,
                        startDestination = HomeScreenDestination
                    )
                    SetupNavigation(scope, navigator.navController)
                }
            }
        }
    }

    @Composable
    private fun SetupNavigation(scope: CoroutineScope, navController: NavController) {
        val currentNavController by rememberUpdatedState(navController)
        LaunchedEffect(scope) {
            navigationCommands
                .onEach { command ->
                    currentNavController.navigateToItem(command)
                }.launchIn(scope)
        }
    }
}