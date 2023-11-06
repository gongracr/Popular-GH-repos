package com.gongracr.ghreposloader.presentation.pager

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.gongracr.core.data.GHProjectsPager
import com.gongracr.core.data.RemoteMediator
import com.gongracr.core.mappers.GHProjectsMapper
import com.gongracr.persistence.dao.GHProjectsDao
import com.gongracr.persistence.database.AppDatabase
import com.gongracr.persistence.model.project.GHProjectEntity
import network.api.ApiConstants.PAGE_SIZE
import network.datasource.GHProjectsRemoteDataSource

@OptIn(ExperimentalPagingApi::class)
@JvmSuppressWildcards
class GHProjectsPagerImpl(
    private val ghProjectsDao: GHProjectsDao,
    private val ghProjectsRemoteDataSource: GHProjectsRemoteDataSource,
    private val database: AppDatabase,
    private val projectsMapper: GHProjectsMapper
) : GHProjectsPager {
    override fun invoke(searchQuery: String): Pager<Int, GHProjectEntity> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                if (searchQuery.isNotEmpty()) {
                    ghProjectsDao.searchPaginatedProjects(searchQuery)
                } else {
                    ghProjectsDao.getAllProjects()
                }
            },
            remoteMediator = RemoteMediator(
                dataSource = ghProjectsRemoteDataSource,
                database = database,
                projectsMapper = projectsMapper,
                isSearchMode = searchQuery.isNotEmpty()
            )
        )
    }
}