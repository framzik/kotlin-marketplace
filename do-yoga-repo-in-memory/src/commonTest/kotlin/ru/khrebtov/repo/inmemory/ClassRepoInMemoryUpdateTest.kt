package ru.khrebtov.repo.inmemory

import ru.khrebtov.do_yoga.RepoClassUpdateTest
import ru.khrebtov.do_yoga.common.repo.IClassRepository

class ClassRepoInMemoryUpdateTest : RepoClassUpdateTest() {
    override val repo: IClassRepository = ClassRepoInMemory(
        initObjects = initObjects,
        randomUuid = { lockNew.asString() }
    )
}
