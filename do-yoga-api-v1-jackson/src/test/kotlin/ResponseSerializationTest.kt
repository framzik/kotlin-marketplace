package ru.otus.otuskotlin.marketplace.api.v1

import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import ru.khrebtov.api.v1.models.ClassCreateResponse
import ru.khrebtov.api.v1.models.ClassResponseObject
import ru.khrebtov.api.v1.models.ClassVisibility
import ru.khrebtov.api.v1.models.IResponse

class ResponseSerializationTest {
    private val response = ClassCreateResponse(
        requestId = "123",
        responseType = "create",
        propertyClass = ClassResponseObject(
            officeAddress = "Строителей 10",
            trainer = "Васильев А.А",
            time = "2017-07-21T17:32:28Z",
            students = setOf("1", "2", "3"),
            visibility = ClassVisibility.PUBLIC
        )
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.writeValueAsString(response)

        assertContains(json, Regex("\"officeAddress\":\\s*\"Строителей 10\""))
        assertContains(json, Regex("\"trainer\":\\s*\"Васильев А.А\""))
        assertContains(json, Regex("\"responseType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(response)
        val obj = apiV1Mapper.readValue(json, IResponse::class.java) as ClassCreateResponse

        assertEquals(response, obj)
    }
}
