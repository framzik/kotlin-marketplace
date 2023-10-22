package ru.khrbetov.biz.validation

import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
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
import ru.khrebtov.do_yoga.common.DoYogaContext
import ru.khrebtov.do_yoga.DoYogaClassStub

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdCorrect(command: DoYogaCommand, processor: DoYogaClassProcessor) = runTest {
    val ctx = DoYogaContext(
        command = command,
        state = DoYogaState.NONE,
        workMode = DoYogaWorkMode.TEST,
        classRequest = DoYogaClassStub.prepareResult {
            lock = DoYogaClassLock("123-234-abc-ABC")
        },
    )
    ctx.addTestPrincipal()
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(DoYogaState.FAILING, ctx.state)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdTrim(command: DoYogaCommand, processor: DoYogaClassProcessor) = runTest {
    val adRequest = DoYogaClassStub.get()
    adRequest.id = DoYogaClassId(" \n\t 666 \n\t ")
    val ctx = DoYogaContext(
        command = command,
        state = DoYogaState.NONE,
        workMode = DoYogaWorkMode.TEST,
        classRequest = DoYogaClass(
            id = DoYogaClassId(" \n\t 123-234-abc-ABC \n\t "),
            officeAddress = "officeAddress",
            trainer = "trainer",
            classType = DoYogaType.PERSONAL,
            visibility = DoYogaVisibility.VISIBLE_PUBLIC,
            lock = DoYogaClassLock("123-234-abc-ABC"),
        ),
    )
    ctx.addTestPrincipal()
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(DoYogaState.FAILING, ctx.state)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdEmpty(command: DoYogaCommand, processor: DoYogaClassProcessor) = runTest {
    val adRequest = DoYogaClassStub.get()
    adRequest.id = DoYogaClassId("")
    val ctx = DoYogaContext(
        command = command,
        state = DoYogaState.NONE,
        workMode = DoYogaWorkMode.TEST,
        classRequest = DoYogaClass(
            id = DoYogaClassId(""),
            officeAddress = "officeAddress",
            trainer = "trainer",
            classType = DoYogaType.PERSONAL,
            visibility = DoYogaVisibility.VISIBLE_PUBLIC,
            lock = DoYogaClassLock("123-234-abc-ABC"),
        ),
    )
    ctx.addTestPrincipal()
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(DoYogaState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("id", error?.field)
    assertContains(error?.message ?: "", "id")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdFormat(command: DoYogaCommand, processor: DoYogaClassProcessor) = runTest {
    val adRequest = DoYogaClassStub.get()
    adRequest.id = DoYogaClassId("!@#\$%^&*(),.{}")
    val ctx = DoYogaContext(
        command = command,
        state = DoYogaState.NONE,
        workMode = DoYogaWorkMode.TEST,
        classRequest = DoYogaClass(
            id = DoYogaClassId("!@#\$%^&*(),.{}"),
            officeAddress = "officeAddress",
            trainer = "trainer",
            classType = DoYogaType.PERSONAL,
            visibility = DoYogaVisibility.VISIBLE_PUBLIC,
            lock = DoYogaClassLock("123-234-abc-ABC"),
        ),
    )
    ctx.addTestPrincipal()
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(DoYogaState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("id", error?.field)
    assertContains(error?.message ?: "", "id")
}
