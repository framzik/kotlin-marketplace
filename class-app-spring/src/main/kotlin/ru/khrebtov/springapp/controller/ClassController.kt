package ru.khrebtov.springapp.controller

import org.springframework.web.bind.annotation.*
import ru.khrebtov.api.v1.models.ClassCreateRequest
import ru.khrebtov.api.v1.models.ClassCreateResponse
import ru.khrebtov.api.v1.models.ClassDeleteRequest
import ru.khrebtov.api.v1.models.ClassDeleteResponse
import ru.khrebtov.api.v1.models.ClassReadRequest
import ru.khrebtov.api.v1.models.ClassReadResponse
import ru.khrebtov.api.v1.models.ClassSearchRequest
import ru.khrebtov.api.v1.models.ClassSearchResponse
import ru.khrebtov.api.v1.models.ClassUpdateRequest
import ru.khrebtov.api.v1.models.ClassUpdateResponse
import ru.otus.otuskotlin.marketplace.app.common.DoYogaAppSettings

@RestController
@RequestMapping("v1/class")
class ClassController(
    private val appSettings: DoYogaAppSettings
) {

    @PostMapping("create")
    suspend fun createClass(@RequestBody request: ClassCreateRequest): ClassCreateResponse =
        processV1(appSettings, request)


    @PostMapping("read")
    suspend fun readClass(@RequestBody request: ClassReadRequest): ClassReadResponse = processV1(appSettings, request)

    @RequestMapping("update", method = [RequestMethod.POST])
    suspend fun updateClass(@RequestBody request: ClassUpdateRequest): ClassUpdateResponse =
        processV1(appSettings, request)

    @PostMapping("delete")
    suspend fun deleteClass(@RequestBody request: ClassDeleteRequest): ClassDeleteResponse =
        processV1(appSettings, request)

    @PostMapping("search")
    suspend fun searchClass(@RequestBody request: ClassSearchRequest): ClassSearchResponse =
        processV1(appSettings, request)
}
