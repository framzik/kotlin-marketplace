package ru.khrebtov.biz.workers

import ru.khrebtov.cor.ICorChainDsl
import ru.khrebtov.cor.worker
import ru.khrebtov.do_yoga.common.DoYogaContext
import ru.khrebtov.do_yoga.common.models.DoYogaState

fun ICorChainDsl<DoYogaContext>.initStatus(title: String) = worker() {
    this.title = title
    on { state == DoYogaState.NONE }
    handle { state = DoYogaState.RUNNING }
}
