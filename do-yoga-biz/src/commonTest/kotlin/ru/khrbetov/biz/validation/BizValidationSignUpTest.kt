package ru.khrbetov.biz.validation

import kotlin.test.Test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.khrebtov.backend.repository.inmemory.ClassRepoStub
import ru.khrebtov.biz.DoYogaClassProcessor
import ru.khrebtov.do_yoga.common.DoYogaCorSettings
import ru.khrebtov.do_yoga.common.models.DoYogaCommand

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationSignUpTest {

    private val command = DoYogaCommand.SIGN_UP
    private val processor = DoYogaClassProcessor(DoYogaCorSettings(repoTest = ClassRepoStub()))

    @Test
    fun correctId() = validationIdCorrect(command, processor)
    @Test
    fun trimId() = validationIdTrim(command, processor)
    @Test
    fun emptyId() = validationIdEmpty(command, processor)
    @Test
    fun badFormatId() = validationIdFormat(command, processor)

}

