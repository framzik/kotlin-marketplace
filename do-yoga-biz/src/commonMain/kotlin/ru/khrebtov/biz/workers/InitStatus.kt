package ru.khrebtov.biz.workers

import ru.khrebtov.cor.ICorChainDsl
import ru.khrebtov.cor.worker
import ru.otus.otuskotlin.marketplace.common.DoYogaContext
import ru.otus.otuskotlin.marketplace.common.models.DoYogaState

fun ICorChainDsl<DoYogaContext>.initStatus(title: String) = worker() {
    this.title = title
    on { state == DoYogaState.NONE }
    handle { state = DoYogaState.RUNNING }
}
