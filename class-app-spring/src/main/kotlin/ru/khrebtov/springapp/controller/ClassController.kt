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
import ru.khrebtov.springapp.service.DoYogaClassBlockingProcessor
import ru.khrebtov.do_yoga.common.DoYogaContext
import ru.khrebtov.do_yoga.fromTransport
import ru.khrebtov.do_yoga.toTransportCreate
import ru.khrebtov.do_yoga.toTransportDelete
import ru.khrebtov.do_yoga.toTransportRead
import ru.khrebtov.do_yoga.toTransportSearch
import ru.khrebtov.do_yoga.toTransportUpdate

@RestController
@RequestMapping("v1/class")
class ClassController(private val processor: DoYogaClassBlockingProcessor) {

    @PostMapping("create")
    fun createAd(@RequestBody request: ClassCreateRequest): ClassCreateResponse {
        val context = DoYogaContext()
        context.fromTransport(request)
        processor.exec(context)
        return context.toTransportCreate()
    }

    @PostMapping("read")
    fun readAd(@RequestBody request: ClassReadRequest): ClassReadResponse {
        val context = DoYogaContext()
        context.fromTransport(request)
        processor.exec(context)
        return context.toTransportRead()
    }

    @RequestMapping("update", method = [RequestMethod.POST])
    fun updateAd(@RequestBody request: ClassUpdateRequest): ClassUpdateResponse {
        val context = DoYogaContext()
        context.fromTransport(request)
        processor.exec(context)
        return context.toTransportUpdate()
    }

    @PostMapping("delete")
    fun deleteAd(@RequestBody request: ClassDeleteRequest): ClassDeleteResponse {
        val context = DoYogaContext()
        context.fromTransport(request)
        processor.exec(context)
        return context.toTransportDelete()
    }

    @PostMapping("search")
    fun searchAd(@RequestBody request: ClassSearchRequest): ClassSearchResponse {
        val context = DoYogaContext()
        context.fromTransport(request)
        processor.exec(context)
        return context.toTransportSearch()
    }
}
