package ru.khrbetov.biz.validation

import kotlin.test.Test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.khrebtov.backend.repository.inmemory.ClassRepoStub
import ru.khrebtov.biz.DoYogaClassProcessor
import ru.khrebtov.do_yoga.common.DoYogaCorSettings
import ru.khrebtov.do_yoga.common.models.DoYogaCommand

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationDeleteTest {

    private val command = DoYogaCommand.DELETE
    private val processor = DoYogaClassProcessor(DoYogaCorSettings(repoTest = ClassRepoStub()))

    @Test
    fun correctId() = validationIdCorrect(command, processor)

    @Test
    fun trimId() = validationIdTrim(command, processor)

    @Test
    fun emptyId() = validationIdEmpty(command, processor)

    @Test
    fun badFormatId() = validationIdFormat(command, processor)

    @Test
    fun correctLock() = validationLockCorrect(command, processor)

    @Test
    fun trimLock() = validationLockTrim(command, processor)

    @Test
    fun emptyLock() = validationLockEmpty(command, processor)

    @Test
    fun badFormatLock() = validationLockFormat(command, processor)
}

