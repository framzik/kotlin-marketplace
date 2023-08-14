package ru.otus.otuskotlin.marketplace.common

import ru.otus.otuskotlin.marketplace.common.repo.IClassRepository

data class DoYogaCorSettings(
    val repoStub: IClassRepository = IClassRepository.NONE,
    val repoTest: IClassRepository = IClassRepository.NONE,
    val repoProd: IClassRepository = IClassRepository.NONE,
) {
    companion object {
        val NONE = DoYogaCorSettings()
    }
}
