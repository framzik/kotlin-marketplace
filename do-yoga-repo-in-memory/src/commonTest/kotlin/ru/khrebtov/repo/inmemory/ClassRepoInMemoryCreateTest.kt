package ru.khrebtov.repo.inmemory

import ru.otus.otuskotlin.marketplace.backend.repo.tests.RepoClassCreateTest

class ClassRepoInMemoryCreateTest : RepoClassCreateTest() {
    override val repo = ClassRepoInMemory(
        initObjects = initObjects,
    )
}