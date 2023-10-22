package ru.khrbetov.biz.repo

import kotlin.test.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.khrbetov.biz.addTestPrincipal
import ru.khrebtov.biz.DoYogaClassProcessor
import ru.khrebtov.do_yoga.common.models.DoYogaClass
import ru.khrebtov.do_yoga.common.models.DoYogaClassId
import ru.khrebtov.do_yoga.common.models.DoYogaClassLock
import ru.khrebtov.do_yoga.common.models.DoYogaCommand
import ru.khrebtov.do_yoga.common.models.DoYogaError
import ru.khrebtov.do_yoga.common.models.DoYogaState
import ru.khrebtov.do_yoga.common.models.DoYogaType
import ru.khrebtov.do_yoga.common.models.DoYogaVisibility
import ru.khrebtov.do_yoga.common.models.DoYogaWorkMode
import ru.khrebtov.do_yoga.ClassRepositoryMock
import ru.khrebtov.do_yoga.common.DoYogaContext
import ru.khrebtov.do_yoga.common.DoYogaCorSettings
import ru.khrebtov.do_yoga.common.repo.DbClassResponse

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
            lock = DoYogaClassLock("123-234-abc-ABC"),
        ),
    )
    ctx.addTestPrincipal()
    processor.exec(ctx)
    assertEquals(DoYogaState.FAILING, ctx.state)
    assertEquals(DoYogaClass(), ctx.classResponse)
    assertEquals(1, ctx.errors.size)
    assertEquals("id", ctx.errors.first().field)
}
