package ru.khrbetov.biz.validation

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.khrebtov.biz.DoYogaClassProcessor
import ru.otus.otuskotlin.marketplace.common.DoYogaContext
import ru.otus.otuskotlin.marketplace.common.models.*

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationSearchTest {

    private val command = DoYogaCommand.SEARCH
    private val processor by lazy { DoYogaClassProcessor() }

    @Test
    fun correctEmpty() = runTest {
        val ctx = DoYogaContext(
            command = command,
            state = DoYogaState.NONE,
            workMode = DoYogaWorkMode.TEST,
            classFilterRequest = DoYogaClassFilter()
        )
        processor.exec(ctx)
        assertEquals(0, ctx.errors.size)
        assertNotEquals(DoYogaState.FAILING, ctx.state)
    }
}

