package ru.otus.otuskotlin.marketplace.mappers.v1


import kotlinx.datetime.LocalDateTime
import ru.khrebtov.api.v1.models.ClassCreateObject
import ru.khrebtov.api.v1.models.ClassCreateRequest
import ru.khrebtov.api.v1.models.ClassDebug
import ru.khrebtov.api.v1.models.ClassDeleteRequest
import ru.khrebtov.api.v1.models.ClassReadRequest
import ru.khrebtov.api.v1.models.ClassRequestDebugMode
import ru.khrebtov.api.v1.models.ClassRequestDebugStubs
import ru.khrebtov.api.v1.models.ClassSearchFilter
import ru.khrebtov.api.v1.models.ClassSearchRequest
import ru.khrebtov.api.v1.models.ClassSignUpRequest
import ru.khrebtov.api.v1.models.ClassType
import ru.khrebtov.api.v1.models.ClassUpdateObject
import ru.khrebtov.api.v1.models.ClassUpdateRequest
import ru.khrebtov.api.v1.models.ClassVisibility
import ru.khrebtov.api.v1.models.IRequest
import ru.otus.otuskotlin.marketplace.common.DoYogaContext
import ru.otus.otuskotlin.marketplace.common.models.DoYogaClass
import ru.otus.otuskotlin.marketplace.common.models.DoYogaClassFilter
import ru.otus.otuskotlin.marketplace.common.models.DoYogaClassId
import ru.otus.otuskotlin.marketplace.common.models.DoYogaCommand
import ru.otus.otuskotlin.marketplace.common.models.DoYogaRequestId
import ru.otus.otuskotlin.marketplace.common.models.DoYogaType
import ru.otus.otuskotlin.marketplace.common.models.DoYogaVisibility
import ru.otus.otuskotlin.marketplace.common.models.DoYogaWorkMode
import ru.otus.otuskotlin.marketplace.common.stubs.DoYogaStubs
import ru.otus.otuskotlin.marketplace.mappers.v1.exceptions.UnknownRequestClass

fun DoYogaContext.fromTransport(request: IRequest) = when (request) {
    is ClassCreateRequest -> fromTransport(request)
    is ClassReadRequest -> fromTransport(request)
    is ClassUpdateRequest -> fromTransport(request)
    is ClassDeleteRequest -> fromTransport(request)
    is ClassSearchRequest -> fromTransport(request)
    is ClassSignUpRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request.javaClass)
}

private fun String?.toAdId() = this?.let { DoYogaClassId(it) } ?: DoYogaClassId.NONE
private fun String?.toAdWithId() = DoYogaClass(id = this.toAdId())
private fun IRequest?.requestId() = this?.requestId?.let { DoYogaRequestId(it) } ?: DoYogaRequestId.NONE

private fun ClassDebug?.transportToWorkMode(): DoYogaWorkMode = when (this?.mode) {
    ClassRequestDebugMode.PROD -> DoYogaWorkMode.PROD
    ClassRequestDebugMode.TEST -> DoYogaWorkMode.TEST
    ClassRequestDebugMode.STUB -> DoYogaWorkMode.STUB
    null -> DoYogaWorkMode.PROD
}

private fun ClassDebug?.transportToStubCase(): DoYogaStubs = when (this?.stub) {
    ClassRequestDebugStubs.SUCCESS -> DoYogaStubs.SUCCESS
    ClassRequestDebugStubs.NOT_FOUND -> DoYogaStubs.NOT_FOUND
    ClassRequestDebugStubs.BAD_ID -> DoYogaStubs.BAD_ID
    ClassRequestDebugStubs.BAD_VISIBILITY -> DoYogaStubs.BAD_VISIBILITY
    ClassRequestDebugStubs.CANNOT_DELETE -> DoYogaStubs.CANNOT_DELETE
    ClassRequestDebugStubs.BAD_SEARCH_STRING -> DoYogaStubs.BAD_SEARCH_STRING
    null -> DoYogaStubs.NONE
}

fun DoYogaContext.fromTransport(request: ClassCreateRequest) {
    command = DoYogaCommand.CREATE
    requestId = request.requestId()
    classRequest = request.propertyClass?.toInternal() ?: DoYogaClass()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun DoYogaContext.fromTransport(request: ClassReadRequest) {
    command = DoYogaCommand.READ
    requestId = request.requestId()
    classRequest = request.propertyClass?.id.toAdWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun DoYogaContext.fromTransport(request: ClassUpdateRequest) {
    command = DoYogaCommand.UPDATE
    requestId = request.requestId()
    classRequest = request.propertyClass?.toInternal() ?: DoYogaClass()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun DoYogaContext.fromTransport(request: ClassDeleteRequest) {
    command = DoYogaCommand.DELETE
    requestId = request.requestId()
    classRequest = request.propertyClass?.id.toAdWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun DoYogaContext.fromTransport(request: ClassSearchRequest) {
    command = DoYogaCommand.SEARCH
    requestId = request.requestId()
    classFilterRequest = request.classFilter.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun DoYogaContext.fromTransport(request: ClassSignUpRequest) {
    command = DoYogaCommand.SIGN_UP
    requestId = request.requestId()
    classRequest = request.propertyClass?.id.toAdWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun ClassSearchFilter?.toInternal(): DoYogaClassFilter = DoYogaClassFilter(
    searchString = this?.searchString ?: ""
)

private fun ClassCreateObject.toInternal(): DoYogaClass = DoYogaClass(
    officeAddress = this.officeAddress,
    trainer = this.trainer,
    students = this.students,
    time = LocalDateTime.parse(this.time!!),
    visibility = this.visibility.fromTransport(),
    classType = this.classType.toInternal()
)

private fun ClassType?.toInternal(): DoYogaType {
    return when (this) {
        ClassType.GROUP -> DoYogaType.GROUP
        ClassType.PERSONAL -> DoYogaType.PERSONAL
        else -> DoYogaType.NONE
    }
}

private fun ClassUpdateObject.toInternal(): DoYogaClass = DoYogaClass(
    id = this.id.toAdId(),
    officeAddress = this.officeAddress,
    trainer = this.trainer,
    students = this.students,
    time = LocalDateTime.parse(this.time!!),
    visibility = this.visibility.fromTransport(),
)

private fun ClassVisibility?.fromTransport(): DoYogaVisibility = when (this) {
    ClassVisibility.PUBLIC -> DoYogaVisibility.VISIBLE_PUBLIC
    ClassVisibility.OWNER_ONLY -> DoYogaVisibility.VISIBLE_TO_OWNER
    ClassVisibility.REGISTERED_ONLY -> DoYogaVisibility.VISIBLE_TO_GROUP
    null -> DoYogaVisibility.NONE
}
