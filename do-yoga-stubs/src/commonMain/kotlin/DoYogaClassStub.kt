package ru.otus.otuskotlin.marketplace.stubs

import ru.otus.otuskotlin.marketplace.common.models.DoYogaClass
import ru.otus.otuskotlin.marketplace.common.models.DoYogaClassId
import ru.otus.otuskotlin.marketplace.common.models.DoYogaType
import ru.otus.otuskotlin.marketplace.stubs.DoYogaStubBolts.CLASS_GROUP
import ru.otus.otuskotlin.marketplace.stubs.DoYogaStubBolts.CLASS_PERSONAL

object DoYogaClassStub {
    fun get(): DoYogaClass = CLASS_PERSONAL.copy()

    fun prepareResult(block: DoYogaClass.() -> Unit): DoYogaClass = get().apply(block)

    fun prepareSearchList(filter: String, type: DoYogaType) = listOf(
        doYogaClassGroup("d-666-01", filter, type),
        doYogaClassGroup("d-666-02", filter, type),
        doYogaClassGroup("d-666-03", filter, type),
        doYogaClassGroup("d-666-04", filter, type),
        doYogaClassGroup("d-666-05", filter, type),
        doYogaClassGroup("d-666-06", filter, type),
    )

    fun prepareOffersList(filter: String, type: DoYogaType) = listOf(
        doYogaClassPersonal("s-666-01", filter, type),
        doYogaClassPersonal("s-666-02", filter, type),
        doYogaClassPersonal("s-666-03", filter, type),
        doYogaClassPersonal("s-666-04", filter, type),
        doYogaClassPersonal("s-666-05", filter, type),
        doYogaClassPersonal("s-666-06", filter, type),
    )

    private fun doYogaClassGroup(id: String, filter: String, type: DoYogaType) =
        doYogaClass(CLASS_GROUP, id = id, filter = filter, type = type)

    private fun doYogaClassPersonal(id: String, filter: String, type: DoYogaType) =
        doYogaClass(CLASS_PERSONAL, id = id, filter = filter, type = type)

    private fun doYogaClass(base: DoYogaClass, id: String, filter: String, type: DoYogaType) = base.copy(
        id = DoYogaClassId(id),
        officeAddress = "$filter ${base.officeAddress}",
        classType = type,
    )

}
