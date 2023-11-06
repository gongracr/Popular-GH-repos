package com.gongracr.core.data

import androidx.paging.Pager
import androidx.paging.PagingData
import com.gongracr.core.domain.repository.GHProjectsRemoteRepository
import com.gongracr.core.errors.CoreFailure
import com.gongracr.core.utility.Either
import com.gongracr.persistence.database.AppDatabase
import com.gongracr.persistence.model.project.GHProjectEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GHProjectsRemoteRepositoryImpl @Inject constructor(
    private val database: AppDatabase,
    private val pager: GHProjectsPager,
    private val ioDispatcher: CoroutineDispatcher
) : GHProjectsRemoteRepository {

    override suspend fun getProjectByID(id: String): Either<CoreFailure.StorageFailure, GHProjectEntity> = withContext(ioDispatcher) {
        try {
            Either.Right(database.ghProjectsDao().findProjectById(id))
        } catch (e: Exception) {
            Either.Left(CoreFailure.StorageFailure.DataNotFound)
        }
    }

    override fun getPopularGHProjectsList(searchQuery: String): Flow<PagingData<GHProjectEntity>> =
        pager(searchQuery).flow.flowOn(ioDispatcher)

    override suspend fun hideProjectByID(id: Long) = withContext(ioDispatcher) {
        try {
            Either.Right(database.ghProjectsDao().deleteProject(id))
        } catch (e: Exception) {
            Either.Left(CoreFailure.StorageFailure.Generic(e))
        }
    }
}

typealias GHProjectsPager = (String) -> Pager<Int, GHProjectEntity>