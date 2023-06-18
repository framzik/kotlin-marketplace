package ru.otus.otuskotlin.marketplace.api.v1

import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import ru.khrebtov.api.v1.models.ClassCreateObject
import ru.khrebtov.api.v1.models.ClassCreateRequest
import ru.khrebtov.api.v1.models.ClassDebug
import ru.khrebtov.api.v1.models.ClassRequestDebugMode
import ru.khrebtov.api.v1.models.ClassRequestDebugStubs
import ru.khrebtov.api.v1.models.ClassVisibility
import ru.khrebtov.api.v1.models.IRequest

class RequestSerializationTest {
    private val request = ClassCreateRequest(
        requestId = "123",
        requestType = "create",
        debug = ClassDebug(
            mode = ClassRequestDebugMode.STUB,
            stub = ClassRequestDebugStubs.BAD_ID
        ),
        propertyClass = ClassCreateObject(
            officeAddress = "Строителей 10",
            trainer = "Васильев А.А",
            time = "2017-07-21T17:32:28Z",
            students = setOf("1","2","3"),
            visibility = ClassVisibility.PUBLIC,
        )
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.writeValueAsString(request)

        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"requestType\":\\s*\"create\""))
        assertContains(json, Regex("\"officeAddress\":\\s*\"Строителей 10\""))
        assertContains(json, Regex("\"trainer\":\\s*\"Васильев А.А\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(request)
        val obj = apiV1Mapper.readValue(json, IRequest::class.java) as ClassCreateRequest

        assertEquals(request, obj)
    }

    @Test
    fun deserializeNaked() {
        val jsonString = """
            {"requestId": "123"}
        """.trimIndent()
        val obj = apiV1Mapper.readValue(jsonString, ClassCreateRequest::class.java)

        assertEquals("123", obj.requestId)
    }
}
