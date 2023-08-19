package ru.otus.otuskotlin.marketplace.common.repo

import ru.otus.otuskotlin.marketplace.common.models.DoYogaClass
import ru.otus.otuskotlin.marketplace.common.models.DoYogaClassId
import ru.otus.otuskotlin.marketplace.common.models.DoYogaClassLock

data class DbClassIdRequest(
    val id: DoYogaClassId,
    val lock: DoYogaClassLock = DoYogaClassLock.NONE,
) {
    constructor(ad: DoYogaClass) : this(ad.id, ad.lock)
}
