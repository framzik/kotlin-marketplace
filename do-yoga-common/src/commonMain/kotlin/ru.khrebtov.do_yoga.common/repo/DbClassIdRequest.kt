package ru.khrebtov.do_yoga.common.repo

import ru.khrebtov.do_yoga.common.models.DoYogaClass
import ru.khrebtov.do_yoga.common.models.DoYogaClassId
import ru.khrebtov.do_yoga.common.models.DoYogaClassLock

data class DbClassIdRequest(
    val id: DoYogaClassId,
    val lock: DoYogaClassLock = DoYogaClassLock.NONE,
) {
    constructor(doYogaClass: DoYogaClass) : this(doYogaClass.id, doYogaClass.lock)
}
