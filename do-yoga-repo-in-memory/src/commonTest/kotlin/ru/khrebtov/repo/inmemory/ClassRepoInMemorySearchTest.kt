package ru.khrebtov.repo.inmemory

import ru.otus.otuskotlin.marketplace.backend.repo.tests.RepoClassSearchTest
import ru.otus.otuskotlin.marketplace.common.repo.IClassRepository

class ClassRepoInMemorySearchTest : RepoClassSearchTest() {
    override val repo: IClassRepository = ClassRepoInMemory(
        initObjects = initObjects
    )
}
