package ru.khrbetov.biz.repo

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.khrebtov.biz.DoYogaClassProcessor
import ru.otus.otuskotlin.marketplace.backend.repo.tests.ClassRepositoryMock
import ru.otus.otuskotlin.marketplace.common.DoYogaContext
import ru.otus.otuskotlin.marketplace.common.DoYogaCorSettings
import ru.otus.otuskotlin.marketplace.common.models.*
import ru.otus.otuskotlin.marketplace.common.repo.DbClassResponse

class BizRepoReadTest {

    private val command = DoYogaCommand.READ
    private val initClass = DoYogaClass(
        id = DoYogaClassId("123"),
        officeAddress = "abc",
        trainer = "abc",
        classType = DoYogaType.PERSONAL,
        visibility = DoYogaVisibility.VISIBLE_PUBLIC,
    )
    private val repo by lazy {
        ClassRepositoryMock(
            invokeReadClass = {
                DbClassResponse(
                    isSuccess = true,
                    data = initClass,
                )
            }
        )
    }
    private val settings by lazy {
        DoYogaCorSettings(
            repoTest = repo
        )
    }
    private val processor by lazy { DoYogaClassProcessor(settings) }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun repoReadSuccessTest() = runTest {
        val ctx = DoYogaContext(
            command = command,
            state = DoYogaState.NONE,
            workMode = DoYogaWorkMode.TEST,
            classRequest = DoYogaClass(
                id = DoYogaClassId("123"),
            ),
        )
        processor.exec(ctx)
        assertEquals(DoYogaState.FINISHING, ctx.state)
        assertEquals(initClass.id, ctx.classResponse.id)
        assertEquals(initClass.officeAddress, ctx.classResponse.officeAddress)
        assertEquals(initClass.trainer, ctx.classResponse.trainer)
        assertEquals(initClass.classType, ctx.classResponse.classType)
        assertEquals(initClass.visibility, ctx.classResponse.visibility)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun repoReadNotFoundTest() = repoNotFoundTest(command)
}
