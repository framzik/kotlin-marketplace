package ru.khrbetov.biz.repo

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.khrebtov.biz.DoYogaClassProcessor
import ru.otus.otuskotlin.marketplace.backend.repo.tests.ClassRepositoryMock
import ru.otus.otuskotlin.marketplace.common.DoYogaContext
import ru.otus.otuskotlin.marketplace.common.DoYogaCorSettings
import ru.otus.otuskotlin.marketplace.common.models.*
import ru.otus.otuskotlin.marketplace.common.repo.DbClassResponse

@OptIn(ExperimentalCoroutinesApi::class)
class BizRepoDeleteTest {

    private val command = DoYogaCommand.DELETE
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
            },
            invokeDeleteClass = {
                if (it.id == initClass.id)
                    DbClassResponse(
                        isSuccess = true,
                        data = initClass
                    )
                else DbClassResponse(isSuccess = false, data = null)
            }
        )
    }
    private val settings by lazy {
        DoYogaCorSettings(
            repoTest = repo
        )
    }
    private val processor by lazy { DoYogaClassProcessor(settings) }

    @Test
    fun repoDeleteSuccessTest() = runTest {
        val classToUpdate = DoYogaClass(
            id = DoYogaClassId("123"),
        )
        val ctx = DoYogaContext(
            command = command,
            state = DoYogaState.NONE,
            workMode = DoYogaWorkMode.TEST,
            classRequest = classToUpdate,
        )
        processor.exec(ctx)
        assertEquals(DoYogaState.FINISHING, ctx.state)
        assertTrue { ctx.errors.isEmpty() }
        assertEquals(initClass.id, ctx.classResponse.id)
        assertEquals(initClass.officeAddress, ctx.classResponse.officeAddress)
        assertEquals(initClass.trainer, ctx.classResponse.trainer)
        assertEquals(initClass.classType, ctx.classResponse.classType)
        assertEquals(initClass.visibility, ctx.classResponse.visibility)
    }

    @Test
    fun repoDeleteNotFoundTest() = repoNotFoundTest(command)
}
