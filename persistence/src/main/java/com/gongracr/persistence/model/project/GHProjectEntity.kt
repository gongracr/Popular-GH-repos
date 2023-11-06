package com.gongracr.persistence.model.project

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repositories")
data class GHProjectEntity(
    @PrimaryKey val id: Long,
    val createdAt: Long,
    val name: String,
    val fullname: String,
    val description: String?,
    val stargazersCount: Int,
    val forksCount: Int,
    val mainLanguage: String?,
    val login: String,
    val htmlUrl: String,
    val avatarUrl: String,
)
