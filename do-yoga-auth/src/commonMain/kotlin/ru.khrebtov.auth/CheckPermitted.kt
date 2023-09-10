package ru.khrebtov.auth

import ru.khrebtov.do_yoga.common.models.DoYogaCommand
import ru.khrebtov.do_yoga.common.permissions.DoYogaPrincipalRelations
import ru.khrebtov.do_yoga.common.permissions.DoYogaUserPermissions

fun checkPermitted(
    command: DoYogaCommand,
    relations: Iterable<DoYogaPrincipalRelations>,
    permissions: Iterable<DoYogaUserPermissions>,
) =
    relations.asSequence().flatMap { relation ->
        permissions.map { permission ->
            AccessTableConditions(
                command = command,
                permission = permission,
                relation = relation,
            )
        }
    }.any {
        accessTable[it] != null
    }

private data class AccessTableConditions(
    val command: DoYogaCommand,
    val permission: DoYogaUserPermissions,
    val relation: DoYogaPrincipalRelations
)

private val accessTable = mapOf(
    // Create
    AccessTableConditions(
        command = DoYogaCommand.CREATE,
        permission = DoYogaUserPermissions.CREATE,
        relation = DoYogaPrincipalRelations.NEW,
    ) to true,
    AccessTableConditions(
        command = DoYogaCommand.CREATE,
        permission = DoYogaUserPermissions.READ_PUBLIC,
        relation = DoYogaPrincipalRelations.NONE,
    ) to true,

    // Read
    AccessTableConditions(
        command = DoYogaCommand.READ,
        permission = DoYogaUserPermissions.READ_PUBLIC,
        relation = DoYogaPrincipalRelations.NONE,
    ) to true,

    // Update
    AccessTableConditions(
        command = DoYogaCommand.UPDATE,
        permission = DoYogaUserPermissions.UPDATE_PUBLIC,
        relation = DoYogaPrincipalRelations.GROUP,
    ) to true,
    AccessTableConditions(
        command = DoYogaCommand.UPDATE,
        permission = DoYogaUserPermissions.READ_PUBLIC,
        relation = DoYogaPrincipalRelations.GROUP,
    ) to true,

    // Delete
    AccessTableConditions(
        command = DoYogaCommand.DELETE,
        permission = DoYogaUserPermissions.DELETE_CANDIDATE,
        relation = DoYogaPrincipalRelations.MODERATABLE,
    ) to true,
    AccessTableConditions(
        command = DoYogaCommand.DELETE,
        permission = DoYogaUserPermissions.READ_PUBLIC,
        relation = DoYogaPrincipalRelations.NONE,
    ) to true,

    // SIGN_UP
    AccessTableConditions(
        command = DoYogaCommand.SIGN_UP,
        permission = DoYogaUserPermissions.READ_PUBLIC,
        relation = DoYogaPrincipalRelations.PUBLIC,
    ) to true,
)
