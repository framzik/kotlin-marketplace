package ru.khrebtov.repo.inmemory

import ru.otus.otuskotlin.marketplace.backend.repo.tests.RepoClassDeleteTest
import ru.otus.otuskotlin.marketplace.common.repo.IClassRepository

class ClassRepoInMemoryDeleteTest : RepoClassDeleteTest() {
    override val repo: IClassRepository = ClassRepoInMemory(
        initObjects = initObjects
    )
}
