package ru.khrebtov.do_yoga.common.models

import kotlinx.datetime.LocalDateTime
import ru.khrebtov.do_yoga.common.permissions.DoYogaPrincipalRelations


data class DoYogaClass(
    var id: DoYogaClassId = DoYogaClassId.NONE,
    var officeAddress: String? = null,
    var visibility: DoYogaVisibility = DoYogaVisibility.NONE,
    var classType: DoYogaType = DoYogaType.NONE,
    var trainer: String? = null,
    var students: Set<String>? = null,
    var time: LocalDateTime? = null,
    var lock: DoYogaClassLock = DoYogaClassLock.NONE,
    val permissionsClient: MutableSet<DoYogaClassPermissionClient> = mutableSetOf(),
    var principalRelations: Set<DoYogaPrincipalRelations> = emptySet(),
){
    fun deepCopy(): DoYogaClass = copy(
        permissionsClient = permissionsClient.toMutableSet(),
    )

    fun isEmpty() = this == NONE

    companion object {
        val NONE = DoYogaClass()
    }
}
