package network.datasource

import network.model.GHProjectDTO
import network.model.SearchBaseModel

interface GHProjectsRemoteDataSource {
    suspend fun getPopularGHRepositories(pageNumber: Long, pageSize: Int): SearchBaseModel
    suspend fun getDetailedProjectInfo(owner: String, repo: String): GHProjectDTO
}