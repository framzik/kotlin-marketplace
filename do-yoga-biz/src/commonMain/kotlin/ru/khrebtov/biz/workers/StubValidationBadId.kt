package ru.khrebtov.biz.workers

import ru.khrebtov.cor.ICorChainDsl
import ru.khrebtov.cor.worker
import ru.khrebtov.do_yoga.common.DoYogaContext
import ru.khrebtov.do_yoga.common.models.DoYogaError
import ru.khrebtov.do_yoga.common.models.DoYogaState
import ru.khrebtov.do_yoga.common.stubs.DoYogaStubs

fun ICorChainDsl<DoYogaContext>.stubValidationBadId(title: String) = worker {
    this.title = title
    on { stubCase == DoYogaStubs.BAD_ID && state == DoYogaState.RUNNING }
    handle {
        state = DoYogaState.FAILING
        this.errors.add(
            DoYogaError(
                group = "validation",
                code = "validation-id",
                field = "id",
                message = "Wrong id field"
            )
        )
    }
}
