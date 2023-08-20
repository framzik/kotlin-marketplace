package ru.khrebtov.repo.inmemory

import ru.khrebtov.do_yoga.RepoClassCreateTest

class ClassRepoInMemoryCreateTest : RepoClassCreateTest() {
    override val repo = ClassRepoInMemory(
        initObjects = initObjects,
        randomUuid = { lockNew.asString() }
    )
}