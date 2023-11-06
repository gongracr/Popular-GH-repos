package network.model

import com.google.gson.annotations.SerializedName

data class GHProjectDTO(
    val id: Long,
    val name: String, // Only project name
    @SerializedName("full_name")
    val fullname: String, // Owner name + project name
    @SerializedName("forks_count")
    val forksCount: Int,
    @SerializedName("html_url")
    val htmlUrl: String,
    val description: String?,
    val owner: GHOwnerDTO,
    @SerializedName("language")
    val mainLanguage: String?,
    @SerializedName("stargazers_count")
    val stargazersCount: Int,
){
    data class GHOwnerDTO(
        val login: String,

        @SerializedName("avatar_url")
        val avatarUrl: String,
    )
}
