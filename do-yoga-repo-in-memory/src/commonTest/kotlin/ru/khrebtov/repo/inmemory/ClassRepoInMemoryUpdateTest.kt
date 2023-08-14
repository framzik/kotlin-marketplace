package ru.khrebtov.repo.inmemory

import ru.otus.otuskotlin.marketplace.backend.repo.tests.RepoClassUpdateTest
import ru.otus.otuskotlin.marketplace.common.repo.IClassRepository

class ClassRepoInMemoryUpdateTest : RepoClassUpdateTest() {
    override val repo: IClassRepository = ClassRepoInMemory(
        initObjects = initObjects,
    )
}
