package com.gongracr.ghreposloader.presentation.navigation

import android.annotation.SuppressLint
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.ramcosta.composedestinations.utils.navGraph
import com.ramcosta.composedestinations.utils.route

@SuppressLint("RestrictedApi")
internal fun NavController.navigateToItem(command: NavigationCommand) {

    fun firstDestination() = currentBackStack.value.firstOrNull { it.route() is DestinationSpec<*> }
    fun lastDestination() = currentBackStack.value.lastOrNull { it.route() is DestinationSpec<*> }
    fun lastNestedGraph() = lastDestination()?.takeIf { it.navGraph() != navGraph }?.navGraph()
    fun firstDestinationWithRoute(route: String) =
        currentBackStack.value.firstOrNull { it.destination.route?.getPrimaryRoute() == route.getPrimaryRoute() }

    fun lastDestinationFromOtherGraph(graph: NavGraphSpec) = currentBackStack.value.lastOrNull { it.navGraph() != graph }

    navigate(command.destination) {
        when (command.backStackMode) {
            BackStackMode.CLEAR_WHOLE, BackStackMode.CLEAR_TILL_START -> {
                val inclusive = command.backStackMode == BackStackMode.CLEAR_WHOLE
                popUpTo(inclusive) { firstDestination() }
            }

            BackStackMode.REMOVE_CURRENT -> {
                popUpTo(true) { lastDestination() }
            }

            BackStackMode.REMOVE_CURRENT_NESTED_GRAPH -> {
                popUpTo(
                    getInclusive = { it.route() is NavGraphSpec },
                    getNavBackStackEntry = { lastNestedGraph()?.let { lastDestinationFromOtherGraph(it) } }
                )
            }

            BackStackMode.REMOVE_CURRENT_AND_REPLACE -> {
                popUpTo(true) { lastDestination() }
                popUpTo(true) { firstDestinationWithRoute(command.destination.route) }
            }

            BackStackMode.UPDATE_EXISTING -> {
                popUpTo(true) { firstDestinationWithRoute(command.destination.route) }
            }

            BackStackMode.NONE -> {
            }
        }
        launchSingleTop = true
        restoreState = true
    }
}

private fun NavOptionsBuilder.popUpTo(
    inclusive: Boolean,
    getNavBackStackEntry: () -> NavBackStackEntry?,
) = popUpTo({ inclusive }, getNavBackStackEntry)

private fun NavOptionsBuilder.popUpTo(
    getInclusive: (NavBackStackEntry) -> Boolean,
    getNavBackStackEntry: () -> NavBackStackEntry?,
) {
    getNavBackStackEntry()?.let { entry ->
        popUpTo(entry.destination.id) {
            this.inclusive = getInclusive(entry)
        }
    }
}

fun String.getPrimaryRoute(): String {
    val splitByQuestion = this.split("?")
    val splitBySlash = this.split("/")

    val primaryRoute = when {
        splitByQuestion.size > 1 -> splitByQuestion[0]
        splitBySlash.size > 1 -> splitBySlash[0]
        else -> this
    }
    return primaryRoute
}