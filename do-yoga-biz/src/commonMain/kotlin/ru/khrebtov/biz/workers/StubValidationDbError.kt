package ru.khrebtov.biz.workers

import ru.khrebtov.cor.ICorChainDsl
import ru.khrebtov.cor.worker
import ru.khrebtov.do_yoga.common.DoYogaContext
import ru.khrebtov.do_yoga.common.models.DoYogaError
import ru.khrebtov.do_yoga.common.models.DoYogaState
import ru.khrebtov.do_yoga.common.stubs.DoYogaStubs

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
