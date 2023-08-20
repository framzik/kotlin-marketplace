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
import ru.khrebtov.do_yoga.common.models.DoYogaWorkMode
import ru.khrebtov.do_yoga.common.DoYogaContext
import ru.khrebtov.do_yoga.common.stubs.DoYogaStubs
import ru.khrebtov.do_yoga.DoYogaClassStub

@OptIn(ExperimentalCoroutinesApi::class)
class ClassReadStubTest {

    private val processor = DoYogaClassProcessor()
    val id = DoYogaClassId("666")

    @Test
    fun read() = runTest {

        val ctx = DoYogaContext(
            command = DoYogaCommand.READ,
            state = DoYogaState.NONE,
            workMode = DoYogaWorkMode.STUB,
            stubCase = DoYogaStubs.SUCCESS,
            classRequest = DoYogaClass(
                id = id,
            ),
        )
        processor.exec(ctx)
        with(DoYogaClassStub.get()) {
            assertEquals(id, ctx.classResponse.id)
            assertEquals(officeAddress, ctx.classResponse.officeAddress)
            assertEquals(trainer, ctx.classResponse.trainer)
            assertEquals(classType, ctx.classResponse.classType)
            assertEquals(visibility, ctx.classResponse.visibility)
        }
    }

    @Test
    fun badId() = runTest {
        val ctx = DoYogaContext(
            command = DoYogaCommand.READ,
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
    fun databaseError() = runTest {
        val ctx = DoYogaContext(
            command = DoYogaCommand.READ,
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
            command = DoYogaCommand.READ,
            state = DoYogaState.NONE,
            workMode = DoYogaWorkMode.STUB,
            stubCase = DoYogaStubs.BAD_OFFICE_ADDRESS,
            classRequest = DoYogaClass(
                id = id,
            ),
        )
        processor.exec(ctx)
        assertEquals(DoYogaClass(), ctx.classResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
    }
}
