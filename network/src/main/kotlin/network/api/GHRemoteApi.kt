package network.api

import network.model.GHProjectDTO
import network.model.SearchBaseModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GHRemoteApi {
    @GET("search/repositories?sort=stars&order=desc")
    suspend fun getPopularRepositories(
        @Query("q") query: String,
        @Query("page") page: Long,
        @Query("per_page") itemsPerPage: Int
    ): SearchBaseModel

    @GET("/repos/{owner}/{repo}")
    suspend fun getDetailedProjectInfo(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
    ): GHProjectDTO
}