package com.gongracr.core.domain.usecases

import com.gongracr.core.domain.model.GHProject
import com.gongracr.core.domain.repository.GHProjectsRemoteRepository
import com.gongracr.core.errors.CoreFailure
import com.gongracr.core.mappers.GHProjectsMapper
import com.gongracr.core.utility.Either
import kotlinx.coroutines.CoroutineScope

interface GetProjectByIdUseCase {
    /**
     * Use case in charge of getting a Github project from the database
     * @param id the id of the [GHProject] to be retrieved
     * @return a [ProjectResult] containing Success or Failure cases
     */
    suspend operator fun invoke(id: String, scope: CoroutineScope): ProjectResult
}

class GetProjectByIdUseCaseImpl(
    private val ghProjectsRemoteRepository: GHProjectsRemoteRepository,
    private val projectsMapper: GHProjectsMapper

) : GetProjectByIdUseCase {
    override suspend operator fun invoke(id: String, scope: CoroutineScope): ProjectResult {
        return when (val result = ghProjectsRemoteRepository.getProjectByID(id)) {
            is Either.Left -> ProjectResult.Failure(CoreFailure.StorageFailure.DataNotFound)
            is Either.Right -> ProjectResult.Success(projectsMapper.toDetailedModel(result.value))
        }
    }
}

sealed class ProjectResult {
    data class Success(val project: GHProject) : ProjectResult()
    data class Failure(val storageFailure: CoreFailure.StorageFailure) : ProjectResult()
}
