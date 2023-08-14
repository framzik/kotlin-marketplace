package ru.khrebtov.biz.repo

import ru.khrebtov.cor.ICorChainDsl
import ru.khrebtov.cor.worker
import ru.otus.otuskotlin.marketplace.common.DoYogaContext
import ru.otus.otuskotlin.marketplace.common.models.DoYogaState

fun ICorChainDsl<DoYogaContext>.repoPrepareSigUp(title: String) = worker {
    this.title = title
    description = "Готовим данные к поиску предложений в БД"
    on { state == DoYogaState.RUNNING }
    handle {
        classRepoPrepare = classRepoRead.deepCopy()
        classRepoDone = classRepoRead.deepCopy()
    }
}
