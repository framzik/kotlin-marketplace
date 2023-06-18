package ru.khrebtov.springapp.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.khrebtov.api.v1.models.ClassSignUpRequest
import ru.khrebtov.api.v1.models.ClassSignUpResponse
import ru.khrebtov.springapp.service.DoYogaClassBlockingProcessor
import ru.otus.otuskotlin.marketplace.common.DoYogaContext
import ru.otus.otuskotlin.marketplace.mappers.v1.fromTransport
import ru.otus.otuskotlin.marketplace.mappers.v1.toTransportSignUp

@RestController
@RequestMapping("v1/class")
class SignUpController(private val processor: DoYogaClassBlockingProcessor) {

    @PostMapping("sign_up")
    fun classSignUp(@RequestBody request: ClassSignUpRequest): ClassSignUpResponse {
        val context = DoYogaContext()
        context.fromTransport(request)
        processor.exec(context)
        return context.toTransportSignUp()
    }
}
