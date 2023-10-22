package ru.khrebtov.springapp.controller

import ru.khrebtov.api.v1.models.IRequest
import ru.khrebtov.api.v1.models.IResponse
import ru.khrebtov.do_yoga.fromTransport
import ru.khrebtov.do_yoga.toTransportClass
import ru.khrebtov.springapp.fakeDoYogaPrincipal
import ru.otus.otuskotlin.marketplace.app.common.DoYogaAppSettings
import ru.otus.otuskotlin.marketplace.app.common.process

suspend inline fun <reified Q : IRequest, reified R : IResponse> processV1(
    appSettings: DoYogaAppSettings,
    request: Q,
): R = appSettings.processor.process(
    fromTransport = {
        fromTransport(request)
        principal = fakeDoYogaPrincipal()
    },
    sendResponse = { toTransportClass() as R },
)
