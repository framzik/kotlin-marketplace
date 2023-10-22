package ru.khrbetov.biz.repo

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.khrbetov.biz.addTestPrincipal
import ru.khrebtov.biz.DoYogaClassProcessor
import ru.khrebtov.do_yoga.common.models.DoYogaClass
import ru.khrebtov.do_yoga.common.models.DoYogaClassId
import ru.khrebtov.do_yoga.common.models.DoYogaClassLock
import ru.khrebtov.do_yoga.common.models.DoYogaCommand
import ru.khrebtov.do_yoga.common.models.DoYogaState
import ru.khrebtov.do_yoga.common.models.DoYogaType
import ru.khrebtov.do_yoga.common.models.DoYogaVisibility
import ru.khrebtov.do_yoga.common.models.DoYogaWorkMode
import ru.khrebtov.do_yoga.ClassRepositoryMock
import ru.khrebtov.do_yoga.common.DoYogaContext
import ru.khrebtov.do_yoga.common.DoYogaCorSettings
import ru.khrebtov.do_yoga.common.models.DoYogaUserId
import ru.khrebtov.do_yoga.common.repo.DbClassResponse

@OptIn(ExperimentalCoroutinesApi::class)
class BizRepoDeleteTest {
    private val userId = DoYogaUserId("321")
    private val command = DoYogaCommand.DELETE
    private val initClass = DoYogaClass(
        id = DoYogaClassId("123"),
        officeAddress = "abc",
        trainer = "abc",
        classType = DoYogaType.PERSONAL,
        visibility = DoYogaVisibility.VISIBLE_PUBLIC,
        lock = DoYogaClassLock("123-234-abc-ABC"),
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
            lock = DoYogaClassLock("123-234-abc-ABC"),
        )
        val ctx = DoYogaContext(
            command = command,
            state = DoYogaState.NONE,
            workMode = DoYogaWorkMode.TEST,
            classRequest = classToUpdate,
        )
        ctx.addTestPrincipal(userId)
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
