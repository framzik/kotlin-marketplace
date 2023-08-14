package ru.khrbetov.biz.validation

import kotlin.test.Test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.khrebtov.backend.repository.inmemory.ClassRepoStub
import ru.khrebtov.biz.DoYogaClassProcessor
import ru.otus.otuskotlin.marketplace.common.DoYogaCorSettings
import ru.otus.otuskotlin.marketplace.common.models.DoYogaCommand

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationSignUpTest {

    private val command = DoYogaCommand.DELETE
    private val settings by lazy {
        DoYogaCorSettings(
            repoTest = ClassRepoStub()
        )
    }
    private val processor by lazy { DoYogaClassProcessor(settings) }

    @Test
    fun correctId() = validationIdCorrect(command, processor)
    @Test
    fun trimId() = validationIdTrim(command, processor)
    @Test
    fun emptyId() = validationIdEmpty(command, processor)
    @Test
    fun badFormatId() = validationIdFormat(command, processor)

}

