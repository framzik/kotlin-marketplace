package ru.khrebtov.repo.inmemory

import ru.khrebtov.do_yoga.RepoClassReadTest
import ru.khrebtov.do_yoga.common.repo.IClassRepository

class ClassRepoInMemoryReadTest: RepoClassReadTest() {
    override val repo: IClassRepository = ClassRepoInMemory(
        initObjects = initObjects
    )
}
