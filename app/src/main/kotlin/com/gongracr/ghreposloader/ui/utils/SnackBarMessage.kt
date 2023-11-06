package com.gongracr.ghreposloader.ui.utils

import androidx.annotation.StringRes
import com.gongracr.ghreposloader.R

sealed class SnackBarMessage(@StringRes val resId: Int,
                             vararg val formatArgs: Any) {
    data class ProjectHiddenSuccess(val name: String) : SnackBarMessage(R.string.screen_home_hidden_project_info_message, name)
    data object ProjectHiddenGenericError : SnackBarMessage(R.string.screen_home_hidden_project_error_info_message)
    data object LoadProjectNetworkError : SnackBarMessage(R.string.network_error)
    data object LoadProjectGenericError : SnackBarMessage(R.string.generic_error_loading_projects)
}