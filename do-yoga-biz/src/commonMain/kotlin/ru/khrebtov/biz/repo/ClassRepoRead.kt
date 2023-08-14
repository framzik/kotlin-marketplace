package ru.khrebtov.biz.repo

import ru.khrebtov.cor.ICorChainDsl
import ru.khrebtov.cor.worker
import ru.otus.otuskotlin.marketplace.common.DoYogaContext
import ru.otus.otuskotlin.marketplace.common.models.DoYogaState
import ru.otus.otuskotlin.marketplace.common.repo.DbClassIdRequest

fun ICorChainDsl<DoYogaContext>.repoRead(title: String) = worker {
    this.title = title
    description = "Чтение класса из БД"
    on { state == DoYogaState.RUNNING }
    handle {
        val request = DbClassIdRequest(classValidated)
        val result = classRepo.readClass(request)
        val resultClass = result.data
        if (result.isSuccess && resultClass != null) {
            classRepoRead = resultClass
        } else {
            state = DoYogaState.FAILING
            errors.addAll(result.errors)
        }
    }
}
