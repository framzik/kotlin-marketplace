package ru.khrebtov.biz.repo

import ru.khrebtov.cor.ICorChainDsl
import ru.khrebtov.cor.worker
import ru.otus.otuskotlin.marketplace.common.DoYogaContext
import ru.otus.otuskotlin.marketplace.common.models.DoYogaState
import ru.otus.otuskotlin.marketplace.common.repo.DbClassRequest

fun ICorChainDsl<DoYogaContext>.repoUpdate(title: String) = worker {
    this.title = title
    on { state == DoYogaState.RUNNING }
    handle {
        val request = DbClassRequest(classRepoPrepare)
        val result = classRepo.updateClass(request)
        val resultAd = result.data
        if (result.isSuccess && resultAd != null) {
            classRepoDone = resultAd
        } else {
            state = DoYogaState.FAILING
            errors.addAll(result.errors)
            classRepoDone
        }
    }
}