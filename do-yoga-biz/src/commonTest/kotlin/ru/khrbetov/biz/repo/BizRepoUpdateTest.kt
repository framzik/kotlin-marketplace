package ru.khrbetov.biz.repo

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.marketplace.backend.repo.tests.ClassRepositoryMock
import ru.otus.otuskotlin.marketplace.common.DoYogaContext
import ru.otus.otuskotlin.marketplace.common.DoYogaCorSettings
import ru.otus.otuskotlin.marketplace.common.models.*
import ru.otus.otuskotlin.marketplace.common.repo.DbClassResponse
import kotlin.test.Test
import kotlin.test.assertEquals
import ru.khrebtov.biz.DoYogaClassProcessor

@OptIn(ExperimentalCoroutinesApi::class)
class BizRepoUpdateTest {

    private val command = DoYogaCommand.UPDATE
    private val initClass = DoYogaClass(
        id = DoYogaClassId("123"),
        officeAddress = "abc",
        trainer = "abc",
        classType = DoYogaType.PERSONAL,
        visibility = DoYogaVisibility.VISIBLE_PUBLIC,
    )
    private val repo by lazy { ClassRepositoryMock(
        invokeReadClass = {
            DbClassResponse(
                isSuccess = true,
                data = initClass,
            )
        },
        invokeUpdateClass = {
            DbClassResponse(
                isSuccess = true,
                data = DoYogaClass(
                    id = DoYogaClassId("123"),
                    officeAddress = "xyz",
                    trainer = "xyz",
                    classType = DoYogaType.GROUP,
                    visibility = DoYogaVisibility.VISIBLE_TO_GROUP,
                )
            )
        }
    ) }
    private val settings by lazy {
        DoYogaCorSettings(
            repoTest = repo
        )
    }
    private val processor by lazy { DoYogaClassProcessor(settings) }

    @Test
    fun repoUpdateSuccessTest() = runTest {
        val classToUpdate = DoYogaClass(
            id = DoYogaClassId("123"),
            officeAddress = "xyz",
            trainer = "xyz",
            classType = DoYogaType.GROUP,
            visibility = DoYogaVisibility.VISIBLE_TO_GROUP,
            lock = DoYogaClassLock("123-234-abc-ABC"),
        )
        val ctx = DoYogaContext(
            command = command,
            state = DoYogaState.NONE,
            workMode = DoYogaWorkMode.TEST,
            classRequest = classToUpdate,
        )
        processor.exec(ctx)
        assertEquals(DoYogaState.FINISHING, ctx.state)
        assertEquals(classToUpdate.id, ctx.classResponse.id)
        assertEquals(classToUpdate.officeAddress, ctx.classResponse.officeAddress)
        assertEquals(classToUpdate.trainer, ctx.classResponse.trainer)
        assertEquals(classToUpdate.classType, ctx.classResponse.classType)
        assertEquals(classToUpdate.visibility, ctx.classResponse.visibility)
    }

    @Test
    fun repoUpdateNotFoundTest() = repoNotFoundTest(command)
}
