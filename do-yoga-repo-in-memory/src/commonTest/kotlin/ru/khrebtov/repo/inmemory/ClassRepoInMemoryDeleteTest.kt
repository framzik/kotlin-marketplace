package ru.khrebtov.repo.inmemory

import ru.khrebtov.do_yoga.RepoClassDeleteTest
import ru.khrebtov.do_yoga.common.repo.IClassRepository

class ClassRepoInMemoryDeleteTest : RepoClassDeleteTest() {
    override val repo: IClassRepository = ClassRepoInMemory(
        initObjects = initObjects
    )
}
