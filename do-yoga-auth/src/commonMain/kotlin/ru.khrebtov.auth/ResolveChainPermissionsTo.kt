package ru.khrebtov.auth

import ru.khrebtov.do_yoga.common.permissions.DoYogaUserGroups
import ru.khrebtov.do_yoga.common.permissions.DoYogaUserPermissions

fun resolveChainPermissions(
    groups: Iterable<DoYogaUserGroups>,
) = mutableSetOf<DoYogaUserPermissions>()
    .apply {
        addAll(groups.flatMap { groupPermissionsAdmits[it] ?: emptySet() })
        removeAll(groups.flatMap { groupPermissionsDenys[it] ?: emptySet() }.toSet())
    }
    .toSet()

private val groupPermissionsAdmits = mapOf(
    DoYogaUserGroups.USER to setOf(
        DoYogaUserPermissions.READ_PUBLIC,
    ),
    DoYogaUserGroups.ADMIN to setOf(),
    DoYogaUserGroups.TRAINER to setOf(
        DoYogaUserPermissions.READ_PUBLIC,
    ),
    DoYogaUserGroups.TEST to setOf(),
    DoYogaUserGroups.BAN to setOf(),
)

private val groupPermissionsDenys = mapOf(
    DoYogaUserGroups.USER to setOf(),
    DoYogaUserGroups.ADMIN to setOf(),
    DoYogaUserGroups.TRAINER to setOf(),
    DoYogaUserGroups.TEST to setOf(),
    DoYogaUserGroups.BAN to setOf(DoYogaUserPermissions.READ_PUBLIC),
)
