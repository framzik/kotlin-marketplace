package ru.otus.otuskotlin.marketplace.common.models

import kotlinx.datetime.LocalDateTime


data class DoYogaClass(
    var id: DoYogaClassId = DoYogaClassId.NONE,
    var officeAddress: String? = null,
    var visibility: DoYogaVisibility = DoYogaVisibility.NONE,
    var classType: DoYogaType = DoYogaType.NONE,
    var trainer: String? = null,
    var students: Set<String>? = null,
    var time: LocalDateTime? = null,
    val permissionsClient: MutableSet<DoYogaClassPermissionClient> = mutableSetOf()
)
