package ru.otus.otuskotlin.marketplace.mappers.v1

import org.junit.Test
import kotlin.test.assertEquals
import ru.khrebtov.api.v1.models.ClassCreateObject
import ru.khrebtov.api.v1.models.ClassCreateRequest
import ru.khrebtov.api.v1.models.ClassCreateResponse
import ru.khrebtov.api.v1.models.ClassDebug
import ru.khrebtov.api.v1.models.ClassRequestDebugMode
import ru.khrebtov.api.v1.models.ClassRequestDebugStubs
import ru.khrebtov.api.v1.models.ClassVisibility
import ru.otus.otuskotlin.marketplace.common.DoYogaContext
import ru.otus.otuskotlin.marketplace.common.models.DoYogaClass
import ru.otus.otuskotlin.marketplace.common.models.DoYogaCommand
import ru.otus.otuskotlin.marketplace.common.models.DoYogaError
import ru.otus.otuskotlin.marketplace.common.models.DoYogaRequestId
import ru.otus.otuskotlin.marketplace.common.models.DoYogaState
import ru.otus.otuskotlin.marketplace.common.models.DoYogaVisibility
import ru.otus.otuskotlin.marketplace.common.models.DoYogaWorkMode
import ru.otus.otuskotlin.marketplace.common.stubs.DoYogaStubs

class MapperTest {
    @Test
    fun fromTransport() {
        val req = ClassCreateRequest(
            requestId = "1234",
            debug = ClassDebug(
                mode = ClassRequestDebugMode.STUB,
                stub = ClassRequestDebugStubs.SUCCESS,
            ),
            propertyClass = ClassCreateObject(
                visibility = ClassVisibility.PUBLIC,
                officeAddress = "Stroitele 10",
                trainer = "Макинтош В.А",
                time = "2028-07-21T17:32:28"
            ),
        )

        val context = DoYogaContext()
        context.fromTransport(req)

        assertEquals(DoYogaStubs.SUCCESS, context.stubCase)
        assertEquals(DoYogaWorkMode.STUB, context.workMode)
        assertEquals("Stroitele 10", context.classRequest.officeAddress)
        assertEquals(DoYogaVisibility.VISIBLE_PUBLIC, context.classRequest.visibility)
    }

    @Test
    fun toTransport() {
        val context = DoYogaContext(
            requestId = DoYogaRequestId("1234"),
            command = DoYogaCommand.CREATE,
            classResponse = DoYogaClass(
                visibility = DoYogaVisibility.VISIBLE_PUBLIC,
                officeAddress = "Stroitele 10",
                trainer = "Макинтош В.А",
            ),
            errors = mutableListOf(
                DoYogaError(
                    code = "err",
                    group = "request",
                    field = "title",
                    message = "wrong title",
                )
            ),
            state = DoYogaState.RUNNING,
        )

        val req = context.toTransportClass() as ClassCreateResponse

        assertEquals("1234", req.requestId)
        assertEquals(ClassVisibility.PUBLIC, req.propertyClass?.visibility)
        assertEquals(1, req.errors?.size)
        assertEquals("err", req.errors?.firstOrNull()?.code)
        assertEquals("request", req.errors?.firstOrNull()?.group)
        assertEquals("title", req.errors?.firstOrNull()?.field)
        assertEquals("wrong title", req.errors?.firstOrNull()?.message)
    }
}
