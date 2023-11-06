package network.datasource

import network.api.GHRemoteApi
import network.model.GHProjectDTO
import network.model.SearchBaseModel

class GHProjectsRemoteDataSourceImpl(
    private val ghProjectsRemoteApi: GHRemoteApi
) : GHProjectsRemoteDataSource {

    override suspend fun getPopularGHRepositories(pageNumber: Long, pageSize: Int): SearchBaseModel =
        ghProjectsRemoteApi.getPopularRepositories("stars:>0", pageNumber, pageSize)

    override suspend fun getDetailedProjectInfo(owner: String, repo: String): GHProjectDTO =
        ghProjectsRemoteApi.getDetailedProjectInfo(owner, repo)
}