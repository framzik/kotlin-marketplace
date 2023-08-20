package ru.khrbetov.biz.stub

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.khrebtov.biz.DoYogaClassProcessor
import ru.khrebtov.do_yoga.common.models.DoYogaClass
import ru.khrebtov.do_yoga.common.models.DoYogaClassId
import ru.khrebtov.do_yoga.common.models.DoYogaCommand
import ru.khrebtov.do_yoga.common.models.DoYogaState
import ru.khrebtov.do_yoga.common.models.DoYogaType
import ru.khrebtov.do_yoga.common.models.DoYogaWorkMode
import ru.khrebtov.do_yoga.common.DoYogaContext
import ru.khrebtov.do_yoga.common.stubs.DoYogaStubs
import ru.khrebtov.do_yoga.DoYogaClassStub

@OptIn(ExperimentalCoroutinesApi::class)
class ClassSignUpStubTest {

    private val processor = DoYogaClassProcessor()
    val id = DoYogaClassId("777")

    @Test
    fun signUp() = runTest {

        val ctx = DoYogaContext(
            command = DoYogaCommand.SIGN_UP,
            state = DoYogaState.NONE,
            workMode = DoYogaWorkMode.STUB,
            stubCase = DoYogaStubs.SUCCESS,
            classRequest = DoYogaClass(
                id = id,
            ),
        )
        processor.exec(ctx)

        assertEquals(id, ctx.classResponse.id)

        with(DoYogaClassStub.get()) {
            assertEquals(officeAddress, ctx.classResponse.officeAddress)
            assertEquals(trainer, ctx.classResponse.trainer)
            assertEquals(classType, ctx.classResponse.classType)
            assertEquals(visibility, ctx.classResponse.visibility)
        }

        assertTrue(ctx.classesResponse.size > 1)
        val first = ctx.classesResponse.firstOrNull() ?: fail("Empty response list")
        assertTrue(first.officeAddress?.contains(ctx.classResponse.officeAddress ?: fail("Empty officeAddress")) ?: false)
        assertTrue(first.trainer?.contains(ctx.classResponse.trainer ?: fail("Empty trainer")) ?: false)
        assertEquals(DoYogaType.PERSONAL, first.classType)
        assertEquals(DoYogaClassStub.get().visibility, first.visibility)
    }

    @Test
    fun badId() = runTest {
        val ctx = DoYogaContext(
            command = DoYogaCommand.SIGN_UP,
            state = DoYogaState.NONE,
            workMode = DoYogaWorkMode.STUB,
            stubCase = DoYogaStubs.BAD_ID,
            classRequest = DoYogaClass(
                id = id,
            ),
        )
        processor.exec(ctx)
        assertEquals(DoYogaClass(), ctx.classResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = DoYogaContext(
            command = DoYogaCommand.SIGN_UP,
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
            command = DoYogaCommand.SIGN_UP,
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
