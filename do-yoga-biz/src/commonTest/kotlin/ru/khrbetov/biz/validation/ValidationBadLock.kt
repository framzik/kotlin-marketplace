package validation

import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.khrebtov.biz.DoYogaClassProcessor
import ru.khrebtov.do_yoga.common.models.DoYogaClass
import ru.khrebtov.do_yoga.common.models.DoYogaClassId
import ru.khrebtov.do_yoga.common.models.DoYogaClassLock
import ru.khrebtov.do_yoga.common.models.DoYogaCommand
import ru.khrebtov.do_yoga.common.models.DoYogaState
import ru.khrebtov.do_yoga.common.models.DoYogaType
import ru.khrebtov.do_yoga.common.models.DoYogaVisibility
import ru.khrebtov.do_yoga.common.models.DoYogaWorkMode
import ru.khrebtov.do_yoga.common.DoYogaContext

@OptIn(ExperimentalCoroutinesApi::class)
fun validationLockCorrect(command: DoYogaCommand, processor: DoYogaClassProcessor) = runTest {
    val ctx = DoYogaContext(
        command = command,
        state = DoYogaState.NONE,
        workMode = DoYogaWorkMode.TEST,
        classRequest = DoYogaClass(
            id = DoYogaClassId("123-234-abc-ABC"),
            officeAddress = "abc",
            trainer = "abc",
            classType = DoYogaType.PERSONAL,
            visibility = DoYogaVisibility.VISIBLE_PUBLIC,
            lock = DoYogaClassLock("123-234-abc-ABC"),
        )
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(DoYogaState.FAILING, ctx.state)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationLockTrim(command: DoYogaCommand, processor: DoYogaClassProcessor) = runTest {
    val ctx = DoYogaContext(
        command = command,
        state = DoYogaState.NONE,
        workMode = DoYogaWorkMode.TEST,
        classRequest = DoYogaClass(
            id = DoYogaClassId("123-234-abc-ABC"),
            officeAddress = "abc",
            trainer = "abc",
            classType = DoYogaType.PERSONAL,
            visibility = DoYogaVisibility.VISIBLE_PUBLIC,
            lock = DoYogaClassLock(" \n\t 123-234-abc-ABC \n\t ")
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(DoYogaState.FAILING, ctx.state)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationLockEmpty(command: DoYogaCommand, processor: DoYogaClassProcessor) = runTest {
    val ctx = DoYogaContext(
        command = command,
        state = DoYogaState.NONE,
        workMode = DoYogaWorkMode.TEST,
        classRequest = DoYogaClass(
            id = DoYogaClassId("123-234-abc-ABC"),
            officeAddress = "abc",
            trainer = "abc",
            classType = DoYogaType.PERSONAL,
            visibility = DoYogaVisibility.VISIBLE_PUBLIC,
            lock = DoYogaClassLock("")
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(DoYogaState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("lock", error?.field)
    assertContains(error?.message ?: "", "id")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationLockFormat(command: DoYogaCommand, processor: DoYogaClassProcessor) = runTest {
    val ctx = DoYogaContext(
        command = command,
        state = DoYogaState.NONE,
        workMode = DoYogaWorkMode.TEST,
        classRequest = DoYogaClass(
            id = DoYogaClassId("123-234-abc-ABC"),
            officeAddress = "abc",
            trainer = "abc",
            classType = DoYogaType.PERSONAL,
            visibility = DoYogaVisibility.VISIBLE_PUBLIC,
            lock = DoYogaClassLock("!@#\$%^&*(),.{}"),
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(DoYogaState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("lock", error?.field)
    assertContains(error?.message ?: "", "id")
}
