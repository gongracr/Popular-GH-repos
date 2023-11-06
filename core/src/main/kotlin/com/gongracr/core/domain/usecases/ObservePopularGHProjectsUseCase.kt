package com.gongracr.core.domain.usecases

import androidx.paging.PagingData
import androidx.paging.map
import com.gongracr.core.domain.model.GHProject
import com.gongracr.core.domain.repository.GHProjectsRemoteRepository
import com.gongracr.core.mappers.GHProjectsMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface ObservePopularGHProjectsUseCase {
    /**
     * Use case in charge of fetching the list of Github projects returning it as a [Flow] of paginated [GHProject]
     */
    suspend operator fun invoke(searchQuery: String): Flow<PagingData<GHProject>>
}

class ObservePopularRepositoriesUseCaseImpl @Inject constructor(
    private val repository: GHProjectsRemoteRepository,
    private val projectsMapper: GHProjectsMapper,
) : ObservePopularGHProjectsUseCase {
    override suspend fun invoke(searchQuery: String): Flow<PagingData<GHProject>> =
        repository.getPopularGHProjectsList(searchQuery = searchQuery)
            .map { pagingData ->
                pagingData.map { projectEntity ->
                    projectsMapper.toCompactModel(projectEntity)
                }
            }
}