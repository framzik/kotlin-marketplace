package ru.otus.otuskotlin.marketplace.common.models

data class DoYogaClassFilter(
    var searchString: String = "",
    var trainer: String = "",
    var dealType: DoYogaType = DoYogaType.NONE,
)
