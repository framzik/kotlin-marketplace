package ru.khrbetov.biz.repo

import kotlin.test.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.khrebtov.biz.DoYogaClassProcessor
import ru.otus.otuskotlin.marketplace.backend.repo.tests.ClassRepositoryMock
import ru.otus.otuskotlin.marketplace.common.DoYogaContext
import ru.otus.otuskotlin.marketplace.common.DoYogaCorSettings
import ru.otus.otuskotlin.marketplace.common.models.*
import ru.otus.otuskotlin.marketplace.common.repo.DbClassResponse

private val initClass = DoYogaClass(
    id = DoYogaClassId("123"),
    officeAddress = "abc",
    trainer = "abc",
    classType = DoYogaType.PERSONAL,
    visibility = DoYogaVisibility.VISIBLE_PUBLIC,
)
private val repo = ClassRepositoryMock(
    invokeReadClass = {
        if (it.id == initClass.id) {
            DbClassResponse(
                isSuccess = true,
                data = initClass,
            )
        } else DbClassResponse(
            isSuccess = false,
            data = null,
            errors = listOf(DoYogaError(message = "Not found", field = "id"))
        )
    }
)
private val settings by lazy {
    DoYogaCorSettings(
        repoTest = repo
    )
}
private val processor by lazy { DoYogaClassProcessor(settings) }

@OptIn(ExperimentalCoroutinesApi::class)
fun repoNotFoundTest(command: DoYogaCommand) = runTest {
    val ctx = DoYogaContext(
        command = command,
        state = DoYogaState.NONE,
        workMode = DoYogaWorkMode.TEST,
        classRequest = DoYogaClass(
            id = DoYogaClassId("12345"),
            officeAddress = "xyz",
            trainer = "xyz",
            classType = DoYogaType.GROUP,
            visibility = DoYogaVisibility.VISIBLE_TO_GROUP,
        ),
    )
    processor.exec(ctx)
    assertEquals(DoYogaState.FAILING, ctx.state)
    assertEquals(DoYogaClass(), ctx.classResponse)
    assertEquals(1, ctx.errors.size)
    assertEquals("id", ctx.errors.first().field)
}
