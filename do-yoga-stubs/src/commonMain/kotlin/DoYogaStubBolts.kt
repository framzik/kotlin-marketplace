package ru.otus.otuskotlin.marketplace.stubs

import kotlinx.datetime.LocalDateTime
import ru.otus.otuskotlin.marketplace.common.models.*

object DoYogaStubBolts {
    val CLASS_PERSONAL: DoYogaClass
        get() = DoYogaClass(
            id = DoYogaClassId("666"),
            officeAddress = "Stroitele 10",
            trainer = "Макинтош В.А",
            visibility = DoYogaVisibility.VISIBLE_PUBLIC,
            classType = DoYogaType.PERSONAL,
            time= LocalDateTime(2023,10,7,12,0),
            permissionsClient = mutableSetOf(
                DoYogaClassPermissionClient.READ,
                DoYogaClassPermissionClient.UPDATE,
                DoYogaClassPermissionClient.DELETE,
                DoYogaClassPermissionClient.MAKE_VISIBLE_PUBLIC,
                DoYogaClassPermissionClient.MAKE_VISIBLE_GROUP,
                DoYogaClassPermissionClient.MAKE_VISIBLE_OWNER,
            ),
            students = setOf("Student1","Student2")
        )
    val CLASS_GROUP = CLASS_PERSONAL.copy(classType = DoYogaType.GROUP)
}
