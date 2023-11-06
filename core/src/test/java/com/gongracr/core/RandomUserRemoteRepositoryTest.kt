package com.gongracr.core

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.testing.asPagingSourceFactory
import androidx.paging.testing.asSnapshot
import androidx.room.withTransaction
import com.gongracr.core.TestUtils.TEST_GH_PROJECT_ENTITY
import com.gongracr.core.data.GHProjectsPager
import com.gongracr.core.data.GHProjectsRemoteRepositoryImpl
import com.gongracr.core.data.RemoteMediator
import com.gongracr.core.errors.CoreFailure
import com.gongracr.core.mappers.GHProjectsMapper
import com.gongracr.core.utility.Either
import com.gongracr.core.utility.isLeft
import com.gongracr.persistence.dao.GHProjectsDao
import com.gongracr.persistence.database.AppDatabase
import com.gongracr.persistence.model.project.GHProjectEntity
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.just
import io.mockk.mockkStatic
import io.mockk.runs
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import network.datasource.GHProjectsRemoteDataSource
import network.model.SearchBaseModel
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalPagingApi::class)
@ExperimentalCoroutinesApi
class GHProjectsRemoteRepositoryImplTest {
    @get:Rule
    val mockkRule = MockKRule(this)

    @Test
    fun `getProjectByUID should return project from database`() = runTest(dispatcher.main()) {
        val projectId = 12345L
        val projectEntity = TEST_GH_PROJECT_ENTITY.copy(id = projectId)
        val (arrangement, repository) = Arrangement()
            .withStoredProject(projectEntity)
            .arrange()

        val result = repository.getProjectByID(projectId.toString())

        verify { arrangement.ghProjectsDao.findProjectById(projectId.toString()) }

        assert(result is Either.Right)
        assert((result as Either.Right).value == projectEntity)
    }

    @Test
    fun `getProjectByUID should return StorageFailure when database call fails`() = runTest(dispatcher.main()) {
        val projectId = "12345"
        val (arrangement, repository) = Arrangement()
            .withDBFailure()
            .arrange()

        val result = repository.getProjectByID(projectId)

        verify { arrangement.ghProjectsDao.findProjectById(projectId) }
        coEvery { arrangement.ghProjectsDataSource.getPopularGHRepositories(any(), any()) }

        assert(result is Either.Left)
        assert((result as Either.Left).value is CoreFailure.StorageFailure.DataNotFound)
    }

    @Test
    fun `hideProjectByUID should return success`() = runTest(dispatcher.main()) {
        val (arrangement, repository) = Arrangement()
            .withProjectDeletionSuccess()
            .arrange()

        val result = repository.hideProjectByID(2)

        assert(result is Either.Right)
        verify { arrangement.ghProjectsDao.deleteProject(2) }
    }

    @Test
    fun `hideProjectByUID should return storage failure if not found`() = runTest(dispatcher.main()) {
        val (arrangement, repository) = Arrangement()
            .withProjectDeletionFailure()
            .arrange()

        val result = repository.hideProjectByID(2)

        assert(result.isLeft())
        verify { arrangement.ghProjectsDao.deleteProject(2) }
    }

    private class Arrangement {

        init {
            MockKAnnotations.init(this, relaxUnitFun = true)
            Dispatchers.setMain(dispatcher.main())
            mockkStatic("androidx.room.RoomDatabaseKt")
            every { database.ghProjectsDao() } returns ghProjectsDao
            every { ghProjectsDao.insertAll(any()) } just runs
        }

        @MockK
        lateinit var ghProjectsDataSource: GHProjectsRemoteDataSource

        @MockK
        lateinit var ghProjectsDao: GHProjectsDao

        @MockK
        lateinit var database: AppDatabase

        var localProjectsList: List<GHProjectEntity> = emptyList()
        var isSearchMode = false

        val projectsMapper: GHProjectsMapper = GHProjectsMapper()

        val testPagingSourceFactory by lazy { localProjectsList.asPagingSourceFactory() }
        val testPagingSource by lazy { testPagingSourceFactory() }
        val testMediator by lazy {
            RemoteMediator(
                dataSource = ghProjectsDataSource,
                database = database,
                projectsMapper = projectsMapper,
                isSearchMode = isSearchMode
            )
        }

        val testPager: GHProjectsPager by lazy {
            { _ ->
                Pager(
                    config = PagingConfig(3),
                    pagingSourceFactory = { testPagingSource },
                    remoteMediator = testMediator
                )
            }
        }

        val repository by lazy {
            GHProjectsRemoteRepositoryImpl(
                database = database,
                pager = testPager,
                ioDispatcher = dispatcher.io()
            )
        }

        fun withStoredProject(project: GHProjectEntity) = apply {
            every { ghProjectsDao.findProjectById(project.id.toString()) } returns project
        }

        fun withDBFailure() = apply {
            every { ghProjectsDao.findProjectById(any()) } throws RuntimeException("Database error")
        }

        fun withProjectDeletionSuccess() = apply {
            every { ghProjectsDao.deleteProject(any()) } just runs
        }

        fun withProjectDeletionFailure() = apply {
            every { ghProjectsDao.deleteProject(any()) } throws RuntimeException("Database error")
        }

        fun withProjectListSuccess(storedProjects: List<GHProjectEntity>, remoteProjects: List<GHProjectEntity>) = apply {
            localProjectsList = storedProjects
            val remoteProjectsList = remoteProjects.map { projectsMapper.toDTO(it) }
            coEvery { ghProjectsDataSource.getPopularGHRepositories(any(), any()) } returns
                    SearchBaseModel(
                        totalCount = remoteProjects.size,
                        incompleteResults = false,
                        items = remoteProjectsList
                    )
            every { ghProjectsDao.insertAll(any()) } answers {
                localProjectsList += remoteProjects
            }
            val transactionLambda = slot<suspend () -> Unit>()
            coEvery { database.withTransaction(capture(transactionLambda)) } coAnswers {
                transactionLambda.captured.invoke()
            }
        }

        fun arrange() = this to repository
    }

    companion object {
        private val dispatcher = TestDispatcherProvider()
    }
}