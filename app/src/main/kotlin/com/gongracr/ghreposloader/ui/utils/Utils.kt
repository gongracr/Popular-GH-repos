package com.gongracr.ghreposloader.ui.utils

import com.gongracr.core.domain.model.GHProject

object Utils {
    val TEST_GH_PROJECT: GHProject = GHProject(
        id = 1,
        name = "AwesomeProject",
        fullname = "ACME Company/AwesomeProject",
        description = "node-lipsum is a NodeJS Module and Command-line Interface combo that provides a service API for lipsum.com, which you may or may not know as the go-to place to generate arbitrary dummy text whenever you need it. Node-lipsum can be used right from the command line by invoking node-lipsum (assuming you've installed it globally) or as a node module. When used as a node module, you can also get access to it's service and parser sub-modules, which can provide fine-grain access to lipsum.com's service :node-lipsum is a NodeJS Module and Command-line Interface combo that provides a service API for lipsum.com, which you may or may not know as the go-to place to generate arbitrary dummy text whenever you need it. Node-lipsum can be used right from the command line by invoking node-lipsum (assuming you've installed it globally) or as a node module. When used as a node module, you can also get access to it's service and parser sub-modules, which can provide fine-grain access to lipsum.com's service.",
        stargazersCount = 28910,
        forksCount = 122150,
        mainLanguage = "Kotlin",
        login = "ACME Company",
        htmlUrl = "someUrl.com",
        avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4"
    )
}