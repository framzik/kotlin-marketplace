package ru.khrebtov.do_yoga

import ru.khrebtov.do_yoga.common.models.DoYogaClass
import ru.khrebtov.do_yoga.common.models.DoYogaClassId
import ru.khrebtov.do_yoga.common.models.DoYogaClassLock
import ru.khrebtov.do_yoga.common.models.DoYogaType
import ru.khrebtov.do_yoga.common.models.DoYogaVisibility

abstract class BaseInitClasses(val op: String) : IInitObjects<DoYogaClass> {
    open val lockOld: DoYogaClassLock = DoYogaClassLock("20000000-0000-0000-0000-000000000001")
    open val lockBad: DoYogaClassLock = DoYogaClassLock("20000000-0000-0000-0000-000000000009")

    fun createInitTestModel(
        suf: String,
        classType: DoYogaType = DoYogaType.GROUP,
        trainer: String = "$suf stub trainer",
        lock: DoYogaClassLock = lockOld,
    ) = DoYogaClass(
        id = DoYogaClassId("class-repo-$op-$suf"),
        officeAddress = "$suf stub",
        trainer = trainer,
        visibility = DoYogaVisibility.VISIBLE_PUBLIC,
        classType = classType,
        lock = lock,
    )
}
