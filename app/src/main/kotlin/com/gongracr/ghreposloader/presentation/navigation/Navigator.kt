package com.gongracr.ghreposloader.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class Navigator(val finish: () -> Unit, val navController: NavHostController) {
    private val isResumed: Boolean
        get() = navController.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED

    /**
     * Navigates to the specified screen.
     * @param navigationCommand command containing the destination and back stack mode
     * @param onlyIfResumed if true, will ignore the navigation action if the current `NavBackStackEntry`
     * is not in the RESUMED state. This avoids duplicate navigation actions and should be used when it's the user action
     * or when we simply don't want to make more than one navigation action at a time (skip some destinations instantly).
     * More here: https://composedestinations.rafaelcosta.xyz/navigation/basics#avoiding-duplicate-navigation
     */
    fun navigate(navigationCommand: NavigationCommand, onlyIfResumed: Boolean = false) {
        if (onlyIfResumed && !isResumed) return
        navController.navigateToItem(navigationCommand)
    }

    /**
     * Navigates back to the previous screen.
     * @param onlyIfResumed if true, will ignore the navigation action if the current `NavBackStackEntry`
     * is not in the RESUMED state. This avoids duplicate navigation actions and should be used when it's the user action
     * or when we simply don't want to make more than one navigation action at a time (skip some destinations instantly).
     * More here: https://composedestinations.rafaelcosta.xyz/navigation/basics#avoiding-duplicate-navigation
     */
    fun navigateBack(onlyIfResumed: Boolean = false) {
        if (onlyIfResumed && !isResumed) return
        if (!navController.popBackStack()) finish()
    }
}

@Composable
fun rememberNavigator(finish: () -> Unit): Navigator {
    val navController = rememberNavController()
    return remember(finish, navController) { Navigator(finish, navController) }
}