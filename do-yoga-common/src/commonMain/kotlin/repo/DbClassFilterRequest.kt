package ru.otus.otuskotlin.marketplace.common.repo

import ru.otus.otuskotlin.marketplace.common.models.DoYogaType

data class DbClassFilterRequest(
    val searchFilter: String = "",
    val trainer: String = "",
    var classType: DoYogaType = DoYogaType.NONE,
)
