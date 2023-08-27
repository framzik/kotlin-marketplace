package ru.khrebtov.repo.inmemory.model

import ru.khrebtov.do_yoga.common.models.DoYogaClass
import ru.khrebtov.do_yoga.common.models.DoYogaClassId
import ru.khrebtov.do_yoga.common.models.DoYogaClassLock
import ru.khrebtov.do_yoga.common.models.DoYogaType
import ru.khrebtov.do_yoga.common.models.DoYogaVisibility

data class ClassEntity(
    val id: String? = null,
    val officeAddress: String? = null,
    val trainer: String? = null,
    val classType: String? = null,
    val visibility: String? = null,
    val lock: String? = null,
) {
    constructor(model: DoYogaClass): this(
        id = model.id.asString().takeIf { it.isNotBlank() },
        officeAddress = model.officeAddress.takeIf { it?.isNotBlank() ?: false },
        trainer = model.trainer.takeIf { it?.isNotBlank()?: false },
        classType = model.classType.takeIf { it != DoYogaType.NONE }?.name,
        visibility = model.visibility.takeIf { it != DoYogaVisibility.NONE }?.name,
        lock = model.lock.asString().takeIf { it.isNotBlank() }
    )

    fun toInternal() = DoYogaClass(
        id = id?.let { DoYogaClassId(it) }?: DoYogaClassId.NONE,
        officeAddress = officeAddress?: "",
        trainer = trainer?: "",
        classType = classType?.let { DoYogaType.valueOf(it) }?: DoYogaType.NONE,
        visibility = visibility?.let { DoYogaVisibility.valueOf(it) }?: DoYogaVisibility.NONE,
        lock = lock?.let { DoYogaClassLock(it) } ?: DoYogaClassLock.NONE,
    )
}
