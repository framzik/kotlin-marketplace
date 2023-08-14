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
import ru.otus.otuskotlin.marketplace.common.repo.DbClassesResponse

class BizRepoSearchTest {

    private val command = DoYogaCommand.SEARCH
    private val initClass = DoYogaClass(
        id = DoYogaClassId("123"),
        officeAddress = "abc",
        trainer = "abc",
        classType = DoYogaType.PERSONAL,
        visibility = DoYogaVisibility.VISIBLE_PUBLIC,
    )
    private val repo by lazy {
        ClassRepositoryMock(
            invokeSearchClass = {
                DbClassesResponse(
                    isSuccess = true,
                    data = listOf(initClass),
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
    fun repoSearchSuccessTest() = runTest {
        val ctx = DoYogaContext(
            command = command,
            state = DoYogaState.NONE,
            workMode = DoYogaWorkMode.TEST,
            classFilterRequest = DoYogaClassFilter(
                searchString = "ab",
                dealType = DoYogaType.GROUP
            ),
        )
        processor.exec(ctx)
        assertEquals(DoYogaState.FINISHING, ctx.state)
        assertEquals(1, ctx.classesResponse.size)
    }
}
