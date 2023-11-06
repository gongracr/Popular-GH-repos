package com.gongracr.core.mappers

import com.gongracr.core.domain.model.GHProject
import com.gongracr.persistence.model.project.GHProjectEntity
import network.model.GHProjectDTO

class GHProjectsMapper {
    fun toDetailedModel(ghProjectEntity: GHProjectEntity): GHProject = with(ghProjectEntity) {
        GHProject(
            id = id,
            name = name,
            fullname = fullname,
            description = description,
            stargazersCount = stargazersCount,
            forksCount = forksCount,
            mainLanguage = mainLanguage,
            login = login,
            htmlUrl = htmlUrl,
            avatarUrl = avatarUrl
        )
    }

    fun toCompactModel(ghProjectEntity: GHProjectEntity): GHProject = with(ghProjectEntity) {
        GHProject(
            id = id,
            name = name,
            fullname = fullname,
            description = description,
            mainLanguage = mainLanguage,
            stargazersCount = stargazersCount,
            forksCount = forksCount,
            login = login,
            htmlUrl = htmlUrl,
            avatarUrl = avatarUrl
        )
    }

    fun toEntity(projectDTO: GHProjectDTO, createdAt: Long): GHProjectEntity = with(projectDTO) {
        GHProjectEntity(
            id = id,
            createdAt = createdAt,
            name = name,
            fullname = fullname,
            description = description,
            stargazersCount = stargazersCount,
            mainLanguage = mainLanguage,
            forksCount = forksCount,
            login = owner.login,
            htmlUrl = htmlUrl,
            avatarUrl = owner.avatarUrl
        )
    }

    fun toDTO(ghProjectEntity: GHProjectEntity): GHProjectDTO = with(ghProjectEntity) {
        GHProjectDTO(
            id = id,
            name = name,
            description = description,
            mainLanguage = mainLanguage,
            stargazersCount = stargazersCount,
            fullname = "$login/$name",
            forksCount = forksCount,
            htmlUrl = htmlUrl,
            owner = GHProjectDTO.GHOwnerDTO(
                login = login,
                avatarUrl = avatarUrl
            )
        )
    }
}