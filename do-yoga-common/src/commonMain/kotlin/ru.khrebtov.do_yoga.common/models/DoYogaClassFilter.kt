package ru.khrebtov.do_yoga.common.models

data class DoYogaClassFilter(
    var searchString: String = "",
    var trainer: String = "",
    var dealType: DoYogaType = DoYogaType.NONE,
    var searchPermissions: MutableSet<DoYogaSearchPermissions> = mutableSetOf(),
)
