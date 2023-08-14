package ru.khrbetov.biz.repo

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.khrebtov.biz.DoYogaClassProcessor
import ru.otus.otuskotlin.marketplace.backend.repo.tests.ClassRepositoryMock
import ru.otus.otuskotlin.marketplace.common.DoYogaContext
import ru.otus.otuskotlin.marketplace.common.DoYogaCorSettings
import ru.otus.otuskotlin.marketplace.common.models.*
import ru.otus.otuskotlin.marketplace.common.repo.DbClassResponse

class BizRepoCreateTest {

    private val command = DoYogaCommand.CREATE
    private val uuid = "10000000-0000-0000-0000-000000000001"
    private val repo = ClassRepositoryMock(
        invokeCreateClass = {
            DbClassResponse(
                isSuccess = true,
                data = DoYogaClass(
                    id = DoYogaClassId(uuid),
                    officeAddress = it.doYogaClass.officeAddress,
                    trainer = it.doYogaClass.trainer,
                    classType = DoYogaType.PERSONAL,
                    visibility = it.doYogaClass.visibility,
                )
            )
        }
    )
    private val settings = DoYogaCorSettings(
        repoTest = repo
    )
    private val processor = DoYogaClassProcessor(settings)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun repoCreateSuccessTest() = runTest {
        val ctx = DoYogaContext(
            command = command,
            state = DoYogaState.NONE,
            workMode = DoYogaWorkMode.TEST,
            classRequest = DoYogaClass(
                officeAddress = "abc",
                trainer = "abc",
                classType = DoYogaType.PERSONAL,
                visibility = DoYogaVisibility.VISIBLE_PUBLIC,
            ),
        )
        processor.exec(ctx)
        assertEquals(DoYogaState.FINISHING, ctx.state)
        assertNotEquals(DoYogaClassId.NONE, ctx.classResponse.id)
        assertEquals("abc", ctx.classResponse.officeAddress)
        assertEquals("abc", ctx.classResponse.trainer)
        assertEquals(DoYogaType.PERSONAL, ctx.classResponse.classType)
        assertEquals(DoYogaVisibility.VISIBLE_PUBLIC, ctx.classResponse.visibility)
    }
}
