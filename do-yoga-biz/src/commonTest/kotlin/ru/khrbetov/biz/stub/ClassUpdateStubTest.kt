package ru.khrbetov.biz.stub

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
import ru.khrebtov.do_yoga.common.DoYogaContext
import ru.khrebtov.do_yoga.common.stubs.DoYogaStubs

@OptIn(ExperimentalCoroutinesApi::class)
class ClassUpdateStubTest {

    private val processor = DoYogaClassProcessor()
    val id = DoYogaClassId("777")
    val officeAddress = "Moscow 676"
    val classType = DoYogaType.PERSONAL
    val trainer = "Arni Shwarc"
    val visibility = DoYogaVisibility.VISIBLE_PUBLIC

    @Test
    fun create() = runTest {

        val ctx = DoYogaContext(
            command = DoYogaCommand.UPDATE,
            state = DoYogaState.NONE,
            workMode = DoYogaWorkMode.STUB,
            stubCase = DoYogaStubs.SUCCESS,
            classRequest = DoYogaClass(
                id = id,
                officeAddress = officeAddress,
                trainer = trainer,
                classType = DoYogaType.PERSONAL,
                visibility = visibility,
            ),
        )
        processor.exec(ctx)
        assertEquals(id, ctx.classResponse.id)
        assertEquals(officeAddress, ctx.classResponse.officeAddress)
        assertEquals(trainer, ctx.classResponse.trainer)
        assertEquals(classType, ctx.classResponse.classType)
        assertEquals(visibility, ctx.classResponse.visibility)
    }

    @Test
    fun badId() = runTest {
        val ctx = DoYogaContext(
            command = DoYogaCommand.UPDATE,
            state = DoYogaState.NONE,
            workMode = DoYogaWorkMode.STUB,
            stubCase = DoYogaStubs.BAD_ID,
            classRequest = DoYogaClass(),
        )
        processor.exec(ctx)
        assertEquals(DoYogaClass(), ctx.classResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badOfficeAddress() = runTest {
        val ctx = DoYogaContext(
            command = DoYogaCommand.UPDATE,
            state = DoYogaState.NONE,
            workMode = DoYogaWorkMode.STUB,
            stubCase = DoYogaStubs.BAD_OFFICE_ADDRESS,
            classRequest = DoYogaClass(
                id = id,
                officeAddress = officeAddress,
                trainer = trainer,
                classType = DoYogaType.PERSONAL,
                visibility = visibility,
            ),
        )
        processor.exec(ctx)
        assertEquals(DoYogaClass(), ctx.classResponse)
        assertEquals("officeAddress", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badTrainer() = runTest {
        val ctx = DoYogaContext(
            command = DoYogaCommand.UPDATE,
            state = DoYogaState.NONE,
            workMode = DoYogaWorkMode.STUB,
            stubCase = DoYogaStubs.BAD_TRAINER,
            classRequest = DoYogaClass(
                id = id,
                officeAddress = officeAddress,
                trainer = trainer,
                classType = DoYogaType.PERSONAL,
                visibility = visibility,
            ),
        )
        processor.exec(ctx)
        assertEquals(DoYogaClass(), ctx.classResponse)
        assertEquals("trainer", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = DoYogaContext(
            command = DoYogaCommand.UPDATE,
            state = DoYogaState.NONE,
            workMode = DoYogaWorkMode.STUB,
            stubCase = DoYogaStubs.DB_ERROR,
            classRequest = DoYogaClass(
                id = id,
            ),
        )
        processor.exec(ctx)
        assertEquals(DoYogaClass(), ctx.classResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = DoYogaContext(
            command = DoYogaCommand.UPDATE,
            state = DoYogaState.NONE,
            workMode = DoYogaWorkMode.STUB,
            stubCase = DoYogaStubs.BAD_SEARCH_STRING,
            classRequest = DoYogaClass(
                id = id,
                officeAddress = officeAddress,
                trainer = trainer,
                classType = DoYogaType.PERSONAL,
                visibility = visibility,
            ),
        )
        processor.exec(ctx)
        assertEquals(DoYogaClass(), ctx.classResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }
}
