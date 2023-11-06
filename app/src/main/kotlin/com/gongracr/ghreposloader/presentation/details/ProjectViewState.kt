package com.gongracr.ghreposloader.presentation.details

import com.gongracr.core.domain.model.GHProject

data class ProjectViewState(
    val isLoading: Boolean = false,
    val project: GHProject? = null,
    val errorMessage: String = "",
)