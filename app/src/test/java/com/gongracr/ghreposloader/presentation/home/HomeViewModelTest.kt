package com.gongracr.ghreposloader.presentation.home

import androidx.paging.PagingData
import app.cash.turbine.test
import com.gongracr.core.domain.model.GHProject
import com.gongracr.core.domain.usecases.HideGHProjectUseCase
import com.gongracr.core.domain.usecases.HideProjectResult
import com.gongracr.core.domain.usecases.ObservePopularGHProjectsUseCase
import com.gongracr.core.errors.CoreFailure
import com.gongracr.ghreposloader.ui.utils.SnackBarMessage
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @Test
    fun `New projects are observed on successful view model init`() = runTest(dispatcher.main()) {
        // Given
        val project1 = dummyGHProject
        val project2 = dummyGHProject.copy(id = 1234, fullname = "Gonzo/MyProject")
        val projectsList = mutableListOf(project1, project2)
        val (arrangement, viewModel) = Arrangement()
            .withObserveProjectsSuccessResult(projectsList)
            .arrange()

        // When
        viewModel.infoMessage.test {
            expectNoEvents()
        }
        viewModel.ghProjectsState.test {
            val initialPagingData = awaitItem()
            assert(initialPagingData != PagingData.empty<GHProject>())
        }
        advanceUntilIdle()

        // Then
        assert(arrangement.loadedProjects.containsAll(projectsList))
        coVerify(exactly = 1) { arrangement.observePopularProjects.invoke("") }
    }

    @Test
    fun `searching for a projects name should update search state on success`() = runTest(dispatcher.main()) {
        // Given
        val project1 = dummyGHProject
        val project2 = dummyGHProject.copy(id = 1234, fullname = "Gonzo/DummyApp")
        val searchQuery = "Gon"
        val projectsList = mutableListOf(project1, project2)
        val (arrangement, viewModel) = Arrangement()
            .withObserveProjectsSuccessResult(projectsList)
            .arrange()

        // When, Then
        advanceUntilIdle()
        coVerify { arrangement.observePopularProjects.invoke("") }

        viewModel.onSearchQueryChanged(searchQuery)
        advanceUntilIdle()

        coVerify { arrangement.observePopularProjects.invoke(searchQuery) }
        val loadedProjects = arrangement.loadedProjects

        assert(loadedProjects.size == 1)
        assert(loadedProjects.contains(project2))
        coVerify { arrangement.observePopularProjects.invoke(searchQuery) }
    }

    @Test
    fun `cancelling searching should set search field to empty state`() = runTest(dispatcher.main()) {
        // Given
        val project1 = dummyGHProject
        val project2 = dummyGHProject.copy(101010)
        val searchQuery = "id-2"
        val projectsList = mutableListOf(project1, project2)
        val (arrangement, viewModel) = Arrangement()
            .withObserveProjectsSuccessResult(projectsList)
            .arrange()

        // When, Then
        viewModel.onSearchQueryChanged(searchQuery)
        advanceUntilIdle()
        coVerify { arrangement.observePopularProjects.invoke(searchQuery) }

        viewModel.onSearchCancelled()
        advanceUntilIdle()
        coVerify { arrangement.observePopularProjects.invoke("") }

        val loadedProjects = arrangement.loadedProjects
        assert(viewModel.search.value.isEmpty())
        assert(loadedProjects.size == 2)
    }

    @Test
    fun `hiding project from list shows a snackbar message correctly`() = runTest(dispatcher.main()) {
        // Given
        val project1 = dummyGHProject
        val project2 = dummyGHProject.copy(id = 112233, name = "WarApp")
        val projectsList = mutableListOf(project1, project2)
        val (arrangement, viewModel) = Arrangement()
            .withHidingProjectSuccess()
            .withObserveProjectsSuccessResult(projectsList)
            .arrange()

        // When, Then
        viewModel.hideProject(project2)
        viewModel.infoMessage.test {
            val msg = awaitItem()
            assert(msg == SnackBarMessage.ProjectHiddenSuccess("WarApp"))
        }
        advanceUntilIdle()

        coVerify(exactly = 1) { arrangement.hideProject.invoke(project2.id) }
    }

    @Test
    fun `given an error when hiding project from list, UI will show correct error snackbar message`() = runTest(dispatcher.main()) {
        // Given
        val project1 = dummyGHProject
        val project2 = dummyGHProject.copy(id = 123456789, fullname = "Rambo/WarApp")
        val projectsList = mutableListOf(project1, project2)
        val (arrangement, viewModel) = Arrangement()
            .withHidingProjectError()
            .withObserveProjectsSuccessResult(projectsList)
            .arrange()

        // When, Then
        viewModel.hideProject(project1)
        viewModel.infoMessage.test {
            val msg = awaitItem()
            assert(msg == SnackBarMessage.ProjectHiddenGenericError)
        }
        advanceUntilIdle()

        coVerify(exactly = 1) { arrangement.hideProject.invoke(project1.id) }
    }

    private class Arrangement {

        @MockK
        lateinit var observePopularProjects: ObservePopularGHProjectsUseCase

        @MockK
        lateinit var hideProject: HideGHProjectUseCase

        // We mock the loaded projects as the paging data loading is already tested on the repository class
        var loadedProjects = mutableListOf<GHProject>()

        init {
            MockKAnnotations.init(this, relaxUnitFun = true)
            Dispatchers.setMain(dispatcher.main())
        }

        val viewModel = HomeViewModel(observePopularProjects, hideProject, dispatcher)

        suspend fun withObserveProjectsSuccessResult(projectsList: MutableList<GHProject>) = apply {
            val querySlot = slot<String>()
            coEvery {
                observePopularProjects.invoke(capture(querySlot))
            } returns flow {
                val query = querySlot.captured
                loadedProjects.clear()
                loadedProjects.addAll(projectsList.filter {
                    it.fullname.contains(query, ignoreCase = true)
                })
                emit(PagingData.from(loadedProjects))
            }
        }

        suspend fun withHidingProjectSuccess() = apply {
            coEvery {
                hideProject.invoke(match { hiddenProjectId ->
                    loadedProjects.removeIf { loadedProject ->
                        hiddenProjectId == loadedProject.id
                    }
                    true
                })
            } returns HideProjectResult.Success
        }

        suspend fun withHidingProjectError() = apply {
            coEvery { hideProject.invoke(any()) } returns HideProjectResult.Failure(CoreFailure.StorageFailure.DataNotFound)
        }

        fun arrange() = this to viewModel
    }

    companion object {
        val dummyGHProject = GHProject(
            id = 123,
            name = "TheTennisApp",
            fullname = "McEnroeLtd/TheTennisApp",
            description = "The Tennis App is a tennis mindfulness app for Android.",
            stargazersCount = 12345678,
            forksCount = 123,
            mainLanguage = "Kotlin",
            login = "McEnroeLtd",
            htmlUrl = "https://some.url",
            avatarUrl = "https://some.avatar.url",
        )

        private val dispatcher = TestDispatcherProvider()
    }
}