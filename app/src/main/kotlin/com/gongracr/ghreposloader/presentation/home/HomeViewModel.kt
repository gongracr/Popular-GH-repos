package com.gongracr.ghreposloader.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.gongracr.core.domain.model.GHProject
import com.gongracr.core.domain.usecases.HideGHProjectUseCase
import com.gongracr.core.domain.usecases.HideProjectResult
import com.gongracr.core.domain.usecases.ObservePopularGHProjectsUseCase
import com.gongracr.core.utility.dispatchers.DispatcherProvider
import com.gongracr.ghreposloader.ui.utils.SnackBarMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import org.jetbrains.annotations.VisibleForTesting
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val observeProjects: ObservePopularGHProjectsUseCase,
    private val hideProject: HideGHProjectUseCase,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val searchDebounceTimeMs = 1000L
    private val _ghProjectsState: MutableStateFlow<PagingData<GHProject>> = MutableStateFlow(PagingData.empty())
    val ghProjectsState: StateFlow<PagingData<GHProject>> get() = _ghProjectsState

    // Search
    @VisibleForTesting
    internal val search = MutableStateFlow("")

    var infoMessage = MutableSharedFlow<SnackBarMessage>()
        private set

    init {
        startObservingProjects()
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun startObservingProjects() {
        viewModelScope.launch {
            search.debounce(searchDebounceTimeMs)
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    observeProjects(query)
                }
                .cachedIn(viewModelScope)
                .flowOn(dispatcherProvider.io())
                .catch { e ->
                    Log.e(TAG, "Error observing projects", e)
                }
                .collect { pagingData ->
                    _ghProjectsState.value = pagingData
                }
        }
    }

    fun hideProject(ghProject: GHProject) = viewModelScope.launch {
        onSnackbarMessage(
            when (hideProject(ghProject.id)) {
                is HideProjectResult.Success -> SnackBarMessage.ProjectHiddenSuccess(ghProject.name)
                is HideProjectResult.Failure -> SnackBarMessage.ProjectHiddenGenericError
            }
        )
    }

    fun onSearchQueryChanged(newQuery: String) {
        if (newQuery.isEmpty()) {
            onSearchCancelled()
        } else {
            search.value = newQuery
        }
    }

    fun onSearchCancelled() {
        search.value = ""
    }

    private fun onSnackbarMessage(type: SnackBarMessage) = viewModelScope.launch {
        infoMessage.emit(type)
    }

    private companion object {
        val TAG = HomeViewModel::class.java.simpleName
    }
}