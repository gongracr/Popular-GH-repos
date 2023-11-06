package com.gongracr.core.domain.repository

import androidx.paging.PagingData
import com.gongracr.core.errors.CoreFailure.StorageFailure
import com.gongracr.core.utility.Either
import com.gongracr.persistence.model.project.GHProjectEntity
import kotlinx.coroutines.flow.Flow

interface GHProjectsRemoteRepository {
    suspend fun getProjectByID(id: String): Either<StorageFailure, GHProjectEntity>
    fun getPopularGHProjectsList(searchQuery: String): Flow<PagingData<GHProjectEntity>>
    suspend fun hideProjectByID(id: Long): Either<StorageFailure, Unit>
}