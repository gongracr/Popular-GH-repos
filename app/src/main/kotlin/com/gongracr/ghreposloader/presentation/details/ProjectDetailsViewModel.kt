package com.gongracr.ghreposloader.presentation.details

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gongracr.core.domain.usecases.GetProjectByIdUseCase
import com.gongracr.core.domain.usecases.ProjectResult
import com.gongracr.core.utility.dispatchers.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectDetailsViewModel @Inject constructor(
    private val getProjectById: GetProjectByIdUseCase,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    var projectState: ProjectViewState by mutableStateOf(ProjectViewState())

    fun loadProjectDetails(id: String) = viewModelScope.launch(dispatcherProvider.io()) {
        when (val result = getProjectById(id, viewModelScope)) {
            is ProjectResult.Failure -> {
                projectState = projectState.copy(isLoading = false, errorMessage = "")
            }

            is ProjectResult.Success -> {
                projectState = projectState.copy(isLoading = false, project = result.project)
                Log.d(TAG, "Project fetched successfully")
            }
        }
    }

    private companion object {
        val TAG = ProjectDetailsViewModel::class.java.simpleName
    }
}