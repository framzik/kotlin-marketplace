package ru.khrbetov.biz.stub

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.khrebtov.biz.DoYogaClassProcessor
import ru.otus.otuskotlin.marketplace.common.DoYogaContext
import ru.otus.otuskotlin.marketplace.common.models.*
import ru.otus.otuskotlin.marketplace.common.stubs.DoYogaStubs
import ru.otus.otuskotlin.marketplace.stubs.DoYogaClassStub

@OptIn(ExperimentalCoroutinesApi::class)
class ClassDeleteStubTest {

    private val processor = DoYogaClassProcessor()
    val id = DoYogaClassId("777")

    @Test
    fun delete() = runTest {

        val ctx = DoYogaContext(
            command = DoYogaCommand.DELETE,
            state = DoYogaState.NONE,
            workMode = DoYogaWorkMode.STUB,
            stubCase = DoYogaStubs.SUCCESS,
            classRequest = DoYogaClass(
                id = id,
            ),
        )
        processor.exec(ctx)

        val stub = DoYogaClassStub.get()
        assertEquals(stub.id, ctx.classResponse.id)
        assertEquals(stub.officeAddress, ctx.classResponse.officeAddress)
        assertEquals(stub.trainer, ctx.classResponse.trainer)
        assertEquals(stub.classType, ctx.classResponse.classType)
        assertEquals(stub.visibility, ctx.classResponse.visibility)
    }

    @Test
    fun badId() = runTest {
        val ctx = DoYogaContext(
            command = DoYogaCommand.DELETE,
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
            command = DoYogaCommand.DELETE,
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
            command = DoYogaCommand.DELETE,
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
