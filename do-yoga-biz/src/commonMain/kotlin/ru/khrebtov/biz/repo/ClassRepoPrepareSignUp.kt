package ru.khrebtov.biz.repo

import ru.khrebtov.cor.ICorChainDsl
import ru.khrebtov.cor.worker
import ru.khrebtov.do_yoga.common.DoYogaContext
import ru.khrebtov.do_yoga.common.models.DoYogaState

fun ICorChainDsl<DoYogaContext>.repoPrepareSigUp(title: String) = worker {
    this.title = title
    description = "Готовим данные к поиску предложений в БД"
    on { state == DoYogaState.RUNNING }
    handle {
        classRepoPrepare = classRepoRead.deepCopy()
        classRepoDone = classRepoRead.deepCopy()
    }
}
