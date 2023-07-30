package ru.khrebtov.biz.workers

import ru.khrebtov.cor.ICorChainDsl
import ru.khrebtov.cor.worker
import ru.otus.otuskotlin.marketplace.common.DoYogaContext
import ru.otus.otuskotlin.marketplace.common.models.DoYogaError
import ru.otus.otuskotlin.marketplace.common.models.DoYogaState
import ru.otus.otuskotlin.marketplace.common.stubs.DoYogaStubs

fun ICorChainDsl<DoYogaContext>.stubDbError(title: String) = worker {
    this.title = title
    on { stubCase == DoYogaStubs.DB_ERROR && state == DoYogaState.RUNNING }
    handle {
        state = DoYogaState.FAILING
        this.errors.add(
            DoYogaError(
                group = "internal",
                code = "internal-db",
                message = "Internal error"
            )
        )
    }
}
