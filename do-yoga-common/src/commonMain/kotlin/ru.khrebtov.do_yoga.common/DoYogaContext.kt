package ru.khrebtov.do_yoga.common

import kotlinx.datetime.Instant
import ru.khrebtov.do_yoga.common.models.DoYogaClass
import ru.khrebtov.do_yoga.common.models.DoYogaClassFilter
import ru.khrebtov.do_yoga.common.models.DoYogaCommand
import ru.khrebtov.do_yoga.common.models.DoYogaError
import ru.khrebtov.do_yoga.common.models.DoYogaRequestId
import ru.khrebtov.do_yoga.common.models.DoYogaState
import ru.khrebtov.do_yoga.common.models.DoYogaWorkMode
import ru.khrebtov.do_yoga.common.repo.IClassRepository
import ru.khrebtov.do_yoga.common.stubs.DoYogaStubs
import ru.khrebtov.do_yoga.common.permissions.DoYogaPrincipalModel
import ru.khrebtov.do_yoga.common.permissions.DoYogaUserPermissions

data class DoYogaContext(
    var command: DoYogaCommand = DoYogaCommand.NONE,
    var state: DoYogaState = DoYogaState.NONE,
    val errors: MutableList<DoYogaError> = mutableListOf(),
    var settings: DoYogaCorSettings = DoYogaCorSettings.NONE,

    var workMode: DoYogaWorkMode = DoYogaWorkMode.PROD,
    var stubCase: DoYogaStubs = DoYogaStubs.NONE,

    var classRepo: IClassRepository = IClassRepository.NONE,
    var classRepoRead: DoYogaClass = DoYogaClass(),
    var classRepoPrepare: DoYogaClass = DoYogaClass(),
    var classRepoDone: DoYogaClass = DoYogaClass(),
    var classesRepoDone: MutableList<DoYogaClass> = mutableListOf(),

    var principal: DoYogaPrincipalModel = DoYogaPrincipalModel.NONE,
    val permissionsChain: MutableSet<DoYogaUserPermissions> = mutableSetOf(),
    var permitted: Boolean = false,

    var requestId: DoYogaRequestId = DoYogaRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var classRequest: DoYogaClass = DoYogaClass(),
    var classFilterRequest: DoYogaClassFilter = DoYogaClassFilter(),
    var classResponse: DoYogaClass = DoYogaClass(),
    var classesResponse: MutableList<DoYogaClass> = mutableListOf(),

    var classValidating: DoYogaClass = DoYogaClass(),
    var classFilterValidating: DoYogaClassFilter = DoYogaClassFilter(),

    var classValidated: DoYogaClass = DoYogaClass(),
    var classFilterValidated: DoYogaClassFilter = DoYogaClassFilter(),
)
