package ru.khrebtov.biz.permissions

import ru.khrebtov.auth.checkPermitted
import ru.khrebtov.auth.resolveRelationsTo
import ru.khrebtov.cor.ICorChainDsl
import ru.khrebtov.cor.chain
import ru.khrebtov.cor.worker
import ru.khrebtov.do_yoga.common.DoYogaContext
import ru.khrebtov.do_yoga.common.helpers.fail
import ru.khrebtov.do_yoga.common.models.DoYogaError
import ru.khrebtov.do_yoga.common.models.DoYogaState

fun ICorChainDsl<DoYogaContext>.accessValidation(title: String) = chain {
    this.title = title
    description = "Вычисление прав доступа по группе принципала и таблице прав доступа"
    on { state == DoYogaState.RUNNING }
    worker("Вычисление отношения объявления к принципалу") {
        classRepoRead.principalRelations = classRepoRead.resolveRelationsTo(principal)
    }
    worker("Вычисление доступа к объявлению") {
        permitted = checkPermitted(command, classRepoRead.principalRelations, permissionsChain)
    }
    worker {
        this.title = "Валидация прав доступа"
        description = "Проверка наличия прав для выполнения операции"
        on { !permitted }
        handle {
            fail(DoYogaError(message = "User is not allowed to perform this operation"))
        }
    }
}

