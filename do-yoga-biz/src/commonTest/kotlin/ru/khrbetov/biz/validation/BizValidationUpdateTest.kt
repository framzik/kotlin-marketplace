package ru.khrbetov.biz.validation

import kotlin.test.Test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.khrebtov.backend.repository.inmemory.ClassRepoStub
import ru.khrebtov.biz.DoYogaClassProcessor
import ru.khrebtov.do_yoga.common.DoYogaCorSettings
import ru.khrebtov.do_yoga.common.models.DoYogaCommand

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationUpdateTest {

    private val command = DoYogaCommand.UPDATE
    private val processor = DoYogaClassProcessor(DoYogaCorSettings(repoTest = ClassRepoStub()))

    @Test
    fun correctOfficeAddress() = validationOfficeAddressCorrect(command, processor)
    @Test
    fun trimOfficeAddress() = validationOfficeAddressTrim(command, processor)
    @Test
    fun emptyOfficeAddress() = validationOfficeAddressEmpty(command, processor)
    @Test
    fun badSymbolsOfficeAddress() = validationOfficeAddressSymbols(command, processor)

    @Test
    fun correctTrainer() = validationTrainerCorrect(command, processor)
    @Test
    fun trimTrainer() = validationTrainerTrim(command, processor)
    @Test
    fun emptyTrainer() = validationTrainerEmpty(command, processor)
    @Test
    fun badSymbolsTrainer() = validationTrainerSymbols(command, processor)

    @Test
    fun correctId() = validationIdCorrect(command, processor)
    @Test
    fun trimId() = validationIdTrim(command, processor)
    @Test
    fun emptyId() = validationIdEmpty(command, processor)
    @Test
    fun badFormatId() = validationIdFormat(command, processor)
}
