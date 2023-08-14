package ru.khrebtov.repo.inmemory.model

import ru.otus.otuskotlin.marketplace.common.models.*

data class ClassEntity(
    val id: String? = null,
    val officeAddress: String? = null,
    val trainer: String? = null,
    val classType: String? = null,
    val visibility: String? = null,
) {
    constructor(model: DoYogaClass): this(
        id = model.id.asString().takeIf { it.isNotBlank() },
        officeAddress = model.officeAddress.takeIf { it?.isNotBlank() ?: false },
        trainer = model.trainer.takeIf { it?.isNotBlank()?: false },
        classType = model.classType.takeIf { it != DoYogaType.NONE }?.name,
        visibility = model.visibility.takeIf { it != DoYogaVisibility.NONE }?.name,
    )

    fun toInternal() = DoYogaClass(
        id = id?.let { DoYogaClassId(it) }?: DoYogaClassId.NONE,
        officeAddress = officeAddress?: "",
        trainer = trainer?: "",
        classType = classType?.let { DoYogaType.valueOf(it) }?: DoYogaType.NONE,
        visibility = visibility?.let { DoYogaVisibility.valueOf(it) }?: DoYogaVisibility.NONE,
    )
}
