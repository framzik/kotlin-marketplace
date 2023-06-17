package ru.otus.otuskotlin.marketplace.common.models

import kotlinx.datetime.LocalDateTime


data class DoYogaClass(
    var id: DoYogaClassId = DoYogaClassId.NONE,
    val officeAddress: String? = null,
    var visibility: DoYogaVisibility = DoYogaVisibility.NONE,
    var classType: DoYogaType = DoYogaType.NONE,
    val trainer: String? = null,
    val students: Set<String>? = null,
    val time: LocalDateTime? = null,
    val permissionsClient: MutableSet<DoYogaClassPermissionClient> = mutableSetOf()
)
