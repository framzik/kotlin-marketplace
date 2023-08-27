package ru.khrebtov.do_yoga.common.repo

import ru.khrebtov.do_yoga.common.models.DoYogaType

data class DbClassFilterRequest(
    val searchFilter: String = "",
    val trainer: String = "",
    var classType: DoYogaType = DoYogaType.NONE,
)
