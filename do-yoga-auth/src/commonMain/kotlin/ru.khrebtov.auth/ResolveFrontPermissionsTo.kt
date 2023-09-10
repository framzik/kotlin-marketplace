package ru.khrebtov.auth

import ru.khrebtov.do_yoga.common.models.DoYogaClassPermissionClient
import ru.khrebtov.do_yoga.common.permissions.DoYogaPrincipalRelations
import ru.khrebtov.do_yoga.common.permissions.DoYogaUserPermissions


fun resolveFrontPermissions(
    permissions: Iterable<DoYogaUserPermissions>,
    relations: Iterable<DoYogaPrincipalRelations>,
) = mutableSetOf<DoYogaClassPermissionClient>()
    .apply {
        for (permission in permissions) {
            for (relation in relations) {
                accessTable[permission]?.get(relation)?.let { this@apply.add(it) }
            }
        }
    }
    .toSet()

private val accessTable = mapOf(
    // READ
    DoYogaUserPermissions.READ_GROUP to mapOf(
        DoYogaPrincipalRelations.GROUP to DoYogaClassPermissionClient.READ
    ),
    DoYogaUserPermissions.READ_PUBLIC to mapOf(
        DoYogaPrincipalRelations.PUBLIC to DoYogaClassPermissionClient.READ
    ),
    DoYogaUserPermissions.READ_CANDIDATE to mapOf(
        DoYogaPrincipalRelations.MODERATABLE to DoYogaClassPermissionClient.READ
    ),

    // UPDATE

    DoYogaUserPermissions.UPDATE_PUBLIC to mapOf(
        DoYogaPrincipalRelations.MODERATABLE to DoYogaClassPermissionClient.UPDATE
    ),
    DoYogaUserPermissions.UPDATE_CANDIDATE to mapOf(
        DoYogaPrincipalRelations.MODERATABLE to DoYogaClassPermissionClient.UPDATE
    ),

    // DELETE
    DoYogaUserPermissions.DELETE_PUBLIC to mapOf(
        DoYogaPrincipalRelations.MODERATABLE to DoYogaClassPermissionClient.DELETE
    ),
    DoYogaUserPermissions.DELETE_CANDIDATE to mapOf(
        DoYogaPrincipalRelations.MODERATABLE to DoYogaClassPermissionClient.DELETE
    ),
)
