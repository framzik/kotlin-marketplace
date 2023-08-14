package ru.khrebtov.biz.repo

import ru.khrebtov.cor.ICorChainDsl
import ru.khrebtov.cor.worker
import ru.otus.otuskotlin.marketplace.common.DoYogaContext
import ru.otus.otuskotlin.marketplace.common.models.DoYogaState
import ru.otus.otuskotlin.marketplace.common.repo.DbClassIdRequest

fun ICorChainDsl<DoYogaContext>.repoDelete(title: String) = worker {
    this.title = title
    description = "Удаление класса из БД по ID"
    on { state == DoYogaState.RUNNING }
    handle {
        val request = DbClassIdRequest(classRepoPrepare)
        val result = classRepo.deleteClass(request)
        if (!result.isSuccess) {
            state = DoYogaState.FAILING
            errors.addAll(result.errors)
        }
        classRepoDone = classRepoRead
    }
}
