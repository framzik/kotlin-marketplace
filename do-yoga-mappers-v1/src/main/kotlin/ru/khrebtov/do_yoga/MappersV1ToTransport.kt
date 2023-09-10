package ru.khrebtov.do_yoga

import ru.khrebtov.api.v1.models.ClassCreateResponse
import ru.khrebtov.api.v1.models.ClassDeleteResponse
import ru.khrebtov.api.v1.models.ClassInitResponse
import ru.khrebtov.api.v1.models.ClassPermissions
import ru.khrebtov.api.v1.models.ClassReadResponse
import ru.khrebtov.api.v1.models.ClassResponseObject
import ru.khrebtov.api.v1.models.ClassSearchResponse
import ru.khrebtov.api.v1.models.ClassSignUpResponse
import ru.khrebtov.api.v1.models.ClassType
import ru.khrebtov.api.v1.models.ClassUpdateResponse
import ru.khrebtov.api.v1.models.ClassVisibility
import ru.khrebtov.api.v1.models.Error
import ru.khrebtov.api.v1.models.IResponse
import ru.khrebtov.api.v1.models.ResponseResult
import ru.khrebtov.do_yoga.common.DoYogaContext
import ru.khrebtov.do_yoga.common.models.DoYogaClass
import ru.khrebtov.do_yoga.common.models.DoYogaClassLock
import ru.khrebtov.do_yoga.common.models.DoYogaClassPermissionClient
import ru.khrebtov.do_yoga.common.models.DoYogaCommand
import ru.khrebtov.do_yoga.common.models.DoYogaError
import ru.khrebtov.do_yoga.common.models.DoYogaState
import ru.khrebtov.do_yoga.common.models.DoYogaType
import ru.khrebtov.do_yoga.common.models.DoYogaVisibility
import ru.khrebtov.do_yoga.exceptions.UnknownDoYogaCommand

fun DoYogaContext.toTransportClass(): IResponse = when (val cmd = command) {
    DoYogaCommand.CREATE -> toTransportCreate()
    DoYogaCommand.READ -> toTransportRead()
    DoYogaCommand.UPDATE -> toTransportUpdate()
    DoYogaCommand.DELETE -> toTransportDelete()
    DoYogaCommand.SEARCH -> toTransportSearch()
    DoYogaCommand.SIGN_UP -> toTransportSignUp()
    DoYogaCommand.NONE -> throw UnknownDoYogaCommand(cmd)
}

fun DoYogaContext.toTransportCreate() = ClassCreateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == DoYogaState.FINISHING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    propertyClass = classResponse.toTransportClass()
)

fun DoYogaContext.toTransportRead() = ClassReadResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ru.khrebtov.do_yoga.common.models.DoYogaState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    propertyClass = classResponse.toTransportClass()
)

fun DoYogaContext.toTransportUpdate() = ClassUpdateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ru.khrebtov.do_yoga.common.models.DoYogaState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    propertyClass = classResponse.toTransportClass()
)

fun DoYogaContext.toTransportDelete() = ClassDeleteResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ru.khrebtov.do_yoga.common.models.DoYogaState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    propertyClass = classResponse.toTransportClass()
)

fun DoYogaContext.toTransportSearch() = ClassSearchResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ru.khrebtov.do_yoga.common.models.DoYogaState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    classes = classesResponse.toTransportClass()
)

fun DoYogaContext.toTransportSignUp() = ClassSignUpResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ru.khrebtov.do_yoga.common.models.DoYogaState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    propertyClass = classResponse.toTransportClass()
)

fun List<DoYogaClass>.toTransportClass(): List<ClassResponseObject>? = this
    .map { it.toTransportClass() }
    .toList()
    .takeIf { it.isNotEmpty() }

fun DoYogaContext.toTransportInit() = ClassInitResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (errors.isEmpty()) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
)

private fun DoYogaClass.toTransportClass(): ClassResponseObject = ClassResponseObject(
    id = id.takeIf { it != ru.khrebtov.do_yoga.common.models.DoYogaClassId.NONE }?.asString(),
    visibility = visibility.toTransportClass(),
    permissions = permissionsClient.toTransportClass(),
    officeAddress = this.officeAddress,
    trainer = this.trainer,
    students = this.students,
    time = this.time.toString(),
    classType = this.classType.toTransportClass(),
    lock = lock.takeIf { it != DoYogaClassLock.NONE }?.asString(),
)

private fun DoYogaType.toTransportClass(): ClassType? {
    return when (this) {
        DoYogaType.GROUP -> ClassType.GROUP
        DoYogaType.PERSONAL -> ClassType.PERSONAL
        else -> null
    }
}

private fun Set<DoYogaClassPermissionClient>.toTransportClass(): Set<ClassPermissions>? = this
    .map { it.toTransportClass() }
    .toSet()
    .takeIf { it.isNotEmpty() }

private fun DoYogaClassPermissionClient.toTransportClass() = when (this) {
    DoYogaClassPermissionClient.READ -> ClassPermissions.READ
    DoYogaClassPermissionClient.UPDATE -> ClassPermissions.UPDATE
    DoYogaClassPermissionClient.MAKE_VISIBLE_OWNER -> ClassPermissions.MAKE_VISIBLE_OWN
    DoYogaClassPermissionClient.MAKE_VISIBLE_GROUP -> ClassPermissions.MAKE_VISIBLE_GROUP
    DoYogaClassPermissionClient.MAKE_VISIBLE_PUBLIC -> ClassPermissions.MAKE_VISIBLE_PUBLIC
    DoYogaClassPermissionClient.DELETE -> ClassPermissions.DELETE
}

private fun DoYogaVisibility.toTransportClass(): ClassVisibility? = when (this) {
    DoYogaVisibility.VISIBLE_PUBLIC -> ClassVisibility.PUBLIC
    DoYogaVisibility.VISIBLE_TO_GROUP -> ClassVisibility.REGISTERED_ONLY
    DoYogaVisibility.VISIBLE_TO_OWNER -> ClassVisibility.OWNER_ONLY
    DoYogaVisibility.NONE -> null
}

private fun List<DoYogaError>.toTransportErrors(): List<Error>? = this
    .map { it.toTransportClass() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun DoYogaError.toTransportClass() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)
