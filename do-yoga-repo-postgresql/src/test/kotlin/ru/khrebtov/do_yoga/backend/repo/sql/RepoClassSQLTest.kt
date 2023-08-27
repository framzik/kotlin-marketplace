package ru.khrebtov.do_yoga.backend.repo.sql

import ru.khrebtov.do_yoga.common.repo.IClassRepository
import ru.khrebtov.do_yoga.RepoClassCreateTest
import ru.khrebtov.do_yoga.RepoClassDeleteTest
import ru.khrebtov.do_yoga.RepoClassReadTest
import ru.khrebtov.do_yoga.RepoClassSearchTest
import ru.khrebtov.do_yoga.RepoClassUpdateTest

class RepoClassesSQLCreateTest : RepoClassCreateTest() {
    override val repo: IClassRepository = SqlTestCompanion.repoUnderTestContainer(
        initObjects,
        randomUuid = { lockNew.asString() },
    )
}

class RepoClassSQLDeleteTest : RepoClassDeleteTest() {
    override val repo: IClassRepository = SqlTestCompanion.repoUnderTestContainer(initObjects)
}

class RepoClassSQLReadTest : RepoClassReadTest() {
    override val repo: IClassRepository = SqlTestCompanion.repoUnderTestContainer(initObjects)
}

class RepoClassSQLSearchTest : RepoClassSearchTest() {
    override val repo: IClassRepository = SqlTestCompanion.repoUnderTestContainer(initObjects)
}

class RepoClassSQLUpdateTest : RepoClassUpdateTest() {
    override val repo: IClassRepository = SqlTestCompanion.repoUnderTestContainer(
        initObjects,
        randomUuid = { lockNew.asString() },
    )
}
