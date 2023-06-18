package ru.otus.otuskotlin.marketplace.common

import kotlinx.datetime.Instant
import ru.otus.otuskotlin.marketplace.common.models.*
import ru.otus.otuskotlin.marketplace.common.stubs.DoYogaStubs

data class DoYogaContext(
    var command: DoYogaCommand = DoYogaCommand.NONE,
    var state: DoYogaState = DoYogaState.NONE,
    val errors: MutableList<DoYogaError> = mutableListOf(),

    var workMode: DoYogaWorkMode = DoYogaWorkMode.PROD,
    var stubCase: DoYogaStubs = DoYogaStubs.NONE,

    var requestId: DoYogaRequestId = DoYogaRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var classRequest: DoYogaClass = DoYogaClass(),
    var classFilterRequest: DoYogaClassFilter = DoYogaClassFilter(),
    var classResponse: DoYogaClass = DoYogaClass(),
    var classesResponse: MutableList<DoYogaClass> = mutableListOf(),
)
