package com.gongracr.core.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.gongracr.core.mappers.GHProjectsMapper
import com.gongracr.persistence.database.AppDatabase
import com.gongracr.persistence.model.project.GHProjectEntity
import network.api.ApiConstants.PAGE_SIZE
import network.datasource.GHProjectsRemoteDataSource

/**
 * Remote mediator in charge of fetching the paginated data from the network and storing it in the database.
 */
@OptIn(ExperimentalPagingApi::class)
class RemoteMediator(
    private val dataSource: GHProjectsRemoteDataSource,
    private val database: AppDatabase,
    private val isSearchMode: Boolean,
    private val projectsMapper: GHProjectsMapper
) : RemoteMediator<Int, GHProjectEntity>() {

    private var nextPage = 1L

    override suspend fun load(loadType: LoadType, state: PagingState<Int, GHProjectEntity>): MediatorResult {
        try {
            Log.d(TAG, "Remote mediator load method invoked with loadType: $loadType and state: $state")
            // Don't load more items if we are in search mode
            if (isSearchMode) {
                return MediatorResult.Success(endOfPaginationReached = true)
            }

            when (loadType) {
                LoadType.REFRESH -> {
                    if (!state.isEmpty()) {
                        Log.d(TAG, "Remote mediator No need to refresh, we already have data")
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                }

                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    state.lastItemOrNull()?.let {
                        nextPage++
                    }
                }
            }

            val publicReposResponse = dataSource.getPopularGHRepositories(nextPage, PAGE_SIZE)

            val data = publicReposResponse.items.map {
                projectsMapper.toEntity(it, System.currentTimeMillis())
            }

            /*  Uncomment this if you want to clear the database on refresh

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.projectDao().deleteAllData()
                } */
            database.ghProjectsDao().insertAll(data)
            //}
            val endReached = state.pages.lastOrNull() == null

            return MediatorResult.Success(endOfPaginationReached = endReached)
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching paginated data", e)
            return MediatorResult.Error(e)
        }
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.SKIP_INITIAL_REFRESH
    }

    companion object {
        private val TAG = RemoteMediator::class.simpleName
    }
}