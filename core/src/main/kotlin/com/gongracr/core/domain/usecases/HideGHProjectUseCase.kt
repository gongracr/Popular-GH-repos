package com.gongracr.core.domain.usecases

import android.database.sqlite.SQLiteConstraintException
import com.gongracr.core.domain.repository.GHProjectsRemoteRepository
import com.gongracr.core.errors.CoreFailure
import java.io.IOException

interface HideGHProjectUseCase {

    /**
     * Use case in charge of hiding a project from the database
     * @param id the id of the project to be hidden
     * @return a [HideProjectResult] containing Success or Failure cases
     */
    suspend operator fun invoke(id: Long): HideProjectResult
}

class HideGHProjectUseCaseImpl(
    private val repository: GHProjectsRemoteRepository
) : HideGHProjectUseCase {
    override suspend fun invoke(id: Long): HideProjectResult = try {
        repository.hideProjectByID(id)
        HideProjectResult.Success
    } catch (e: SQLiteConstraintException) {
        HideProjectResult.Failure(CoreFailure.StorageFailure.DataNotFound)
    } catch (e: IllegalAccessError) {
        HideProjectResult.Failure(CoreFailure.StorageFailure.Generic(e))
    } catch (e: IOException) {
        HideProjectResult.Failure(CoreFailure.StorageFailure.Generic(e))
    }
}

sealed class HideProjectResult {
    data object Success : HideProjectResult()
    data class Failure(val cause: CoreFailure) : HideProjectResult()
}
