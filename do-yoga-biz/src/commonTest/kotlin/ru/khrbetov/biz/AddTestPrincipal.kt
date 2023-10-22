package ru.khrbetov.biz

import ru.khrebtov.do_yoga.common.DoYogaContext
import ru.khrebtov.do_yoga.common.models.DoYogaUserId
import ru.khrebtov.do_yoga.common.permissions.DoYogaPrincipalModel
import ru.khrebtov.do_yoga.common.permissions.DoYogaUserGroups

fun DoYogaContext.addTestPrincipal(userId: DoYogaUserId = DoYogaUserId("321")) {
    principal = DoYogaPrincipalModel(
        id = userId,
        groups = setOf(
            DoYogaUserGroups.USER,
            DoYogaUserGroups.TRAINER,
            DoYogaUserGroups.TEST,
        )
    )
}
