package com.gongracr.core

import com.gongracr.persistence.model.project.GHProjectEntity

object TestUtils {
    val TEST_GH_PROJECT_ENTITY: GHProjectEntity = GHProjectEntity(
        id = 123,
        name = "DummyApp",
        fullname = "ACME/DummyApp",
        description = "This is a dummy app",
        stargazersCount = 100,
        forksCount = 50,
        mainLanguage = "Java",
        login = "ACME",
        htmlUrl = "http://www.acme.com",
        avatarUrl = "http://www.acme.com/avatar",
        createdAt = 1234567890
    )
}