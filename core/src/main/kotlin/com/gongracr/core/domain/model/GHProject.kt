package com.gongracr.core.domain.model

/**
 * Data class representing a compact GH project with limited information
 */
data class GHProject(
    val id: Long,
    val name: String,
    val fullname: String,
    val description: String?,
    val forksCount: Int,
    val mainLanguage: String?,
    val stargazersCount: Int,
    val login: String,
    val htmlUrl: String,
    val avatarUrl: String,
)
