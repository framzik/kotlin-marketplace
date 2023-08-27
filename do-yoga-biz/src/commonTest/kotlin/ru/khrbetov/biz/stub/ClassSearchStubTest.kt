package ru.khrbetov.biz.stub

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.khrebtov.biz.DoYogaClassProcessor
import ru.khrebtov.do_yoga.common.models.DoYogaClass
import ru.khrebtov.do_yoga.common.models.DoYogaClassFilter
import ru.khrebtov.do_yoga.common.models.DoYogaCommand
import ru.khrebtov.do_yoga.common.models.DoYogaState
import ru.khrebtov.do_yoga.common.models.DoYogaWorkMode
import ru.khrebtov.do_yoga.common.DoYogaContext
import ru.khrebtov.do_yoga.common.stubs.DoYogaStubs
import ru.khrebtov.do_yoga.DoYogaClassStub

@OptIn(ExperimentalCoroutinesApi::class)
class ClassSearchStubTest {

    private val processor = DoYogaClassProcessor()
    val filter = DoYogaClassFilter(searchString = "bolt", trainer="Макинтош В.А")

    @Test
    fun read() = runTest {

        val ctx = DoYogaContext(
            command = DoYogaCommand.SEARCH,
            state = DoYogaState.NONE,
            workMode = DoYogaWorkMode.STUB,
            stubCase = DoYogaStubs.SUCCESS,
            classFilterRequest = filter,
        )
        processor.exec(ctx)
        assertTrue(ctx.classesResponse.size > 1)
        val first = ctx.classesResponse.firstOrNull() ?: fail("Empty response list")
        assertTrue(first.officeAddress?.contains(filter.searchString) ?: fail("Empty officeAddress"))
        assertTrue(first.trainer?.contains(filter.trainer) ?: fail("Empty trainer"))
        with(DoYogaClassStub.get()) {
            assertEquals(classType, first.classType)
            assertEquals(visibility, first.visibility)
        }
    }

    @Test
    fun badId() = runTest {
        val ctx = DoYogaContext(
            command = DoYogaCommand.SEARCH,
            state = DoYogaState.NONE,
            workMode = DoYogaWorkMode.STUB,
            stubCase = DoYogaStubs.BAD_ID,
            classFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(DoYogaClass(), ctx.classResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = DoYogaContext(
            command = DoYogaCommand.SEARCH,
            state = DoYogaState.NONE,
            workMode = DoYogaWorkMode.STUB,
            stubCase = DoYogaStubs.DB_ERROR,
            classFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(DoYogaClass(), ctx.classResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = DoYogaContext(
            command = DoYogaCommand.SEARCH,
            state = DoYogaState.NONE,
            workMode = DoYogaWorkMode.STUB,
            stubCase = DoYogaStubs.BAD_OFFICE_ADDRESS,
            classFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(DoYogaClass(), ctx.classResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
    }
}
