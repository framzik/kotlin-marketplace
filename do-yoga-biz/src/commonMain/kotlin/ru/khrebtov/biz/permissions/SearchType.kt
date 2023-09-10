package ru.khrebtov.biz.permissions

import ru.khrebtov.cor.ICorChainDsl
import ru.khrebtov.cor.chain
import ru.khrebtov.cor.worker
import ru.khrebtov.do_yoga.common.DoYogaContext
import ru.khrebtov.do_yoga.common.models.DoYogaSearchPermissions
import ru.khrebtov.do_yoga.common.models.DoYogaState
import ru.khrebtov.do_yoga.common.permissions.DoYogaUserPermissions

fun ICorChainDsl<DoYogaContext>.searchTypes(title: String) = chain {
    this.title = title
    description = "Добавление ограничений в поисковый запрос согласно правам доступа и др. политикам"
    on { state == DoYogaState.RUNNING }
    worker("Определение типа поиска") {
        classFilterValidated.searchPermissions = setOfNotNull(
            DoYogaSearchPermissions.OWN.takeIf { permissionsChain.contains(DoYogaUserPermissions.SEARCH_PUBLIC) },
            DoYogaSearchPermissions.PUBLIC.takeIf { permissionsChain.contains(DoYogaUserPermissions.SEARCH_PUBLIC) },
            DoYogaSearchPermissions.REGISTERED.takeIf { permissionsChain.contains(DoYogaUserPermissions.SEARCH_REGISTERED) },
        ).toMutableSet()
    }
}
