package ru.khrebtov.springapp.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import ru.khrebtov.api.v1.models.ClassCreateRequest
import ru.khrebtov.api.v1.models.ClassDeleteRequest
import ru.khrebtov.api.v1.models.ClassReadRequest
import ru.khrebtov.api.v1.models.ClassSearchRequest
import ru.khrebtov.api.v1.models.ClassSignUpRequest
import ru.khrebtov.api.v1.models.ClassUpdateRequest
import ru.khrebtov.springapp.service.DoYogaClassBlockingProcessor
import ru.otus.otuskotlin.marketplace.common.DoYogaContext
import ru.otus.otuskotlin.marketplace.mappers.v1.*

@WebMvcTest(ClassController::class, SignUpController::class)
internal class ClassControllerTest {
    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var mapper: ObjectMapper

    @MockBean
    private lateinit var processor: DoYogaClassBlockingProcessor

    @Test
    fun createAd() = testStubClass(
        "/v1/class/create",
        ClassCreateRequest(),
        DoYogaContext().toTransportCreate()
    )

    @Test
    fun readAd() = testStubClass(
        "/v1/class/read",
        ClassReadRequest(),
        DoYogaContext().toTransportRead()
    )

    @Test
    fun updateAd() = testStubClass(
        "/v1/class/update",
        ClassUpdateRequest(),
        DoYogaContext().toTransportUpdate()
    )

    @Test
    fun deleteAd() = testStubClass(
        "/v1/class/delete",
        ClassDeleteRequest(),
        DoYogaContext().toTransportDelete()
    )

    @Test
    fun searchAd() = testStubClass(
        "/v1/class/search",
        ClassSearchRequest(),
        DoYogaContext().toTransportSearch()
    )

    @Test
    fun searchOffers() = testStubClass(
        "/v1/class/sign_up",
        ClassSignUpRequest(),
        DoYogaContext().toTransportSignUp()
    )

    private fun <Req : Any, Res : Any> testStubClass(
        url: String,
        requestObj: Req,
        responseObj: Res,
    ) {
        val request = mapper.writeValueAsString(requestObj)
        val response = mapper.writeValueAsString(responseObj)

        mvc.perform(
            post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        )
            .andExpect(status().isOk)
            .andExpect(content().json(response))
    }
}
