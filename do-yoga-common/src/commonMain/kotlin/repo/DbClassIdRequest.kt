package ru.otus.otuskotlin.marketplace.common.repo

import ru.otus.otuskotlin.marketplace.common.models.DoYogaClass
import ru.otus.otuskotlin.marketplace.common.models.DoYogaClassId

data class DbClassIdRequest(
    val id: DoYogaClassId,
) {
    constructor(ad: DoYogaClass) : this(ad.id)
}
