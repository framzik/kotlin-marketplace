package ru.khrebtov.repo.inmemory

import ru.khrebtov.do_yoga.RepoClassSearchTest
import ru.khrebtov.do_yoga.common.repo.IClassRepository

class ClassRepoInMemorySearchTest : RepoClassSearchTest() {
    override val repo: IClassRepository = ClassRepoInMemory(
        initObjects = initObjects
    )
}
