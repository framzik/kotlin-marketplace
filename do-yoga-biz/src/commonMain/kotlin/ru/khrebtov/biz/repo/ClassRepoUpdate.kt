package ru.khrebtov.biz.repo

import ru.khrebtov.cor.ICorChainDsl
import ru.khrebtov.cor.worker
import ru.khrebtov.do_yoga.common.DoYogaContext
import ru.khrebtov.do_yoga.common.models.DoYogaState
import ru.khrebtov.do_yoga.common.repo.DbClassRequest

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
