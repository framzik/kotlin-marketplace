package ru.khrbetov.biz.validation

import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.khrebtov.biz.DoYogaClassProcessor
import ru.otus.otuskotlin.marketplace.common.DoYogaContext
import ru.otus.otuskotlin.marketplace.common.models.*

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdCorrect(command: DoYogaCommand, processor: DoYogaClassProcessor) = runTest {
    val ctx = DoYogaContext(
        command = command,
        state = DoYogaState.NONE,
        workMode = DoYogaWorkMode.TEST,
        classRequest = DoYogaClass(
            id = DoYogaClassId("123-234-abc-ABC"),
            officeAddress = "officeAddress",
            trainer = "trainer",
            classType = DoYogaType.PERSONAL,
            visibility = DoYogaVisibility.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(DoYogaState.FAILING, ctx.state)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdTrim(command: DoYogaCommand, processor: DoYogaClassProcessor) = runTest {
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
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(DoYogaState.FAILING, ctx.state)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdEmpty(command: DoYogaCommand, processor: DoYogaClassProcessor) = runTest {
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
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(DoYogaState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("id", error?.field)
    assertContains(error?.message ?: "", "id")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdFormat(command: DoYogaCommand, processor: DoYogaClassProcessor) = runTest {
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
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(DoYogaState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("id", error?.field)
    assertContains(error?.message ?: "", "id")
}
