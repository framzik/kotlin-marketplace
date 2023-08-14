package ru.otus.otuskotlin.marketplace.backend.repo.tests

import ru.otus.otuskotlin.marketplace.common.models.DoYogaClass
import ru.otus.otuskotlin.marketplace.common.models.DoYogaClassId
import ru.otus.otuskotlin.marketplace.common.models.DoYogaType
import ru.otus.otuskotlin.marketplace.common.models.DoYogaVisibility

abstract class BaseInitClasses(val op: String) : IInitObjects<DoYogaClass> {

    fun createInitTestModel(
        suf: String,
        classType: DoYogaType = DoYogaType.GROUP,
        trainer: String = "$suf stub trainer"
    ) = DoYogaClass(
        id = DoYogaClassId("class-repo-$op-$suf"),
        officeAddress = "$suf stub",
        trainer = trainer,
        visibility = DoYogaVisibility.VISIBLE_PUBLIC,
        classType = classType,
    )
}
