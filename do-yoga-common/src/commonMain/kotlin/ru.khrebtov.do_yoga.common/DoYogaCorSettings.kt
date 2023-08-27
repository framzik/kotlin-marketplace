package ru.khrebtov.do_yoga.common

import ru.khrebtov.do_yoga.common.repo.IClassRepository

data class DoYogaCorSettings(
    val repoStub: IClassRepository = IClassRepository.NONE,
    val repoTest: IClassRepository = IClassRepository.NONE,
    val repoProd: IClassRepository = IClassRepository.NONE,
) {
    companion object {
        val NONE = DoYogaCorSettings()
    }
}
