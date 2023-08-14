package ru.khrebtov.repo.inmemory

import ru.otus.otuskotlin.marketplace.backend.repo.tests.RepoClassReadTest
import ru.otus.otuskotlin.marketplace.common.repo.IClassRepository

class ClassRepoInMemoryReadTest: RepoClassReadTest() {
    override val repo: IClassRepository = ClassRepoInMemory(
        initObjects = initObjects
    )
}
