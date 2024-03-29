package ru.khrbetov.biz.validation

import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.khrebtov.biz.DoYogaClassProcessor
import ru.khrebtov.do_yoga.common.models.DoYogaClass
import ru.khrebtov.do_yoga.common.models.DoYogaClassLock
import ru.khrebtov.do_yoga.common.models.DoYogaCommand
import ru.khrebtov.do_yoga.common.models.DoYogaState
import ru.khrebtov.do_yoga.common.models.DoYogaType
import ru.khrebtov.do_yoga.common.models.DoYogaVisibility
import ru.khrebtov.do_yoga.common.models.DoYogaWorkMode
import ru.khrebtov.do_yoga.common.DoYogaContext
import ru.khrebtov.do_yoga.DoYogaClassStub

private val stub = DoYogaClassStub.get()

@OptIn(ExperimentalCoroutinesApi::class)
fun validationTrainerCorrect(command: DoYogaCommand, processor: DoYogaClassProcessor) = runTest {
    val ctx = DoYogaContext(
        command = command,
        state = DoYogaState.NONE,
        workMode = DoYogaWorkMode.TEST,
        classRequest = DoYogaClass(
            id = stub.id,
            officeAddress = "abc",
            trainer = "trainer",
            classType = DoYogaType.PERSONAL,
            visibility = DoYogaVisibility.VISIBLE_PUBLIC,
            lock = DoYogaClassLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(DoYogaState.FAILING, ctx.state)
    assertEquals("trainer", ctx.classValidated.trainer)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationTrainerTrim(command: DoYogaCommand, processor: DoYogaClassProcessor) = runTest {
    val ctx = DoYogaContext(
        command = command,
        state = DoYogaState.NONE,
        workMode = DoYogaWorkMode.TEST,
        classRequest = DoYogaClass(
            id = stub.id,
            officeAddress = "abc",
            trainer = " \n\tabc \n\t",
            classType = DoYogaType.PERSONAL,
            visibility = DoYogaVisibility.VISIBLE_PUBLIC,
            lock = DoYogaClassLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(DoYogaState.FAILING, ctx.state)
    assertEquals("abc", ctx.classValidated.trainer)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationTrainerEmpty(command: DoYogaCommand, processor: DoYogaClassProcessor) = runTest {
    val ctx = DoYogaContext(
        command = command,
        state = DoYogaState.NONE,
        workMode = DoYogaWorkMode.TEST,
        classRequest = DoYogaClass(
            id = stub.id,
            officeAddress = "abc",
            trainer = "",
            classType = DoYogaType.PERSONAL,
            visibility = DoYogaVisibility.VISIBLE_PUBLIC,
            lock = DoYogaClassLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(DoYogaState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("trainer", error?.field)
    assertContains(error?.message ?: "", "trainer")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationTrainerSymbols(command: DoYogaCommand, processor: DoYogaClassProcessor) = runTest {
    val ctx = DoYogaContext(
        command = command,
        state = DoYogaState.NONE,
        workMode = DoYogaWorkMode.TEST,
        classRequest = DoYogaClass(
            id = stub.id,
            officeAddress = "abc",
            trainer = "!@#$%^&*(),.{}",
            classType = DoYogaType.PERSONAL,
            visibility = DoYogaVisibility.VISIBLE_PUBLIC,
            lock = DoYogaClassLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(DoYogaState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("trainer", error?.field)
    assertContains(error?.message ?: "", "trainer")
}
