package ru.khrbetov.biz.repo

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.khrebtov.biz.DoYogaClassProcessor
import ru.khrebtov.do_yoga.common.models.DoYogaClass
import ru.khrebtov.do_yoga.common.models.DoYogaClassId
import ru.khrebtov.do_yoga.common.models.DoYogaCommand
import ru.khrebtov.do_yoga.common.models.DoYogaState
import ru.khrebtov.do_yoga.common.models.DoYogaType
import ru.khrebtov.do_yoga.common.models.DoYogaVisibility
import ru.khrebtov.do_yoga.common.models.DoYogaWorkMode
import ru.khrebtov.do_yoga.ClassRepositoryMock
import ru.khrebtov.do_yoga.common.DoYogaContext
import ru.khrebtov.do_yoga.common.DoYogaCorSettings
import ru.khrebtov.do_yoga.common.repo.DbClassResponse
import ru.khrebtov.do_yoga.common.repo.DbClassesResponse

class BizRepoSignUpTest {

    private val command = DoYogaCommand.SIGN_UP
    private val initClass = DoYogaClass(
        id = DoYogaClassId("123"),
        officeAddress = "abc",
        trainer = "abc",
        classType = DoYogaType.PERSONAL,
        visibility = DoYogaVisibility.VISIBLE_PUBLIC,
    )
    private val signUpClass = DoYogaClass(
        id = DoYogaClassId("321"),
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
                    data = initClass
                )
            },
            invokeSearchClass = {
                DbClassesResponse(
                    isSuccess = true,
                    data = listOf(signUpClass)
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
    fun repoOffersSuccessTest() = runTest {
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
        assertEquals(1, ctx.classesResponse.size)
        assertEquals(DoYogaType.PERSONAL, ctx.classesResponse.first().classType)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun repoOffersNotFoundTest() = repoNotFoundTest(command)
}
