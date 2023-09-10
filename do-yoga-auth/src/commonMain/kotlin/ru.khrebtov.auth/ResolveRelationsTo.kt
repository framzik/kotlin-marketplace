package ru.khrebtov.auth

import ru.khrebtov.do_yoga.common.models.DoYogaClass
import ru.khrebtov.do_yoga.common.models.DoYogaClassId
import ru.khrebtov.do_yoga.common.models.DoYogaVisibility.VISIBLE_PUBLIC
import ru.khrebtov.do_yoga.common.models.DoYogaVisibility.VISIBLE_TO_OWNER
import ru.khrebtov.do_yoga.common.permissions.DoYogaPrincipalModel
import ru.khrebtov.do_yoga.common.permissions.DoYogaPrincipalRelations
import ru.khrebtov.do_yoga.common.permissions.DoYogaUserGroups


fun DoYogaClass.resolveRelationsTo(principal: DoYogaPrincipalModel): Set<DoYogaPrincipalRelations> = setOfNotNull(
    DoYogaPrincipalRelations.NONE,
    DoYogaPrincipalRelations.NEW.takeIf { id == DoYogaClassId.NONE },
    DoYogaPrincipalRelations.MODERATABLE.takeIf { visibility != VISIBLE_TO_OWNER },
    DoYogaPrincipalRelations.GROUP.takeIf { principal.groups.contains(DoYogaUserGroups.TRAINER) },
    DoYogaPrincipalRelations.PUBLIC.takeIf { visibility == VISIBLE_PUBLIC },
)
