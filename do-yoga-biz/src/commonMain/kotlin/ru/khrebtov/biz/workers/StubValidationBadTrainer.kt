package ru.khrebtov.biz.workers

import ru.khrebtov.cor.ICorChainDsl
import ru.khrebtov.cor.worker
import ru.khrebtov.do_yoga.common.DoYogaContext
import ru.khrebtov.do_yoga.common.models.DoYogaError
import ru.khrebtov.do_yoga.common.models.DoYogaState
import ru.khrebtov.do_yoga.common.stubs.DoYogaStubs

fun ICorChainDsl<DoYogaContext>.stubValidationBadTrainer(title: String) = worker {
    this.title = title
    on { stubCase == DoYogaStubs.BAD_TRAINER && state == DoYogaState.RUNNING }
    handle {
        state = DoYogaState.FAILING
        this.errors.add(
            DoYogaError(
                group = "validation",
                code = "validation-trainer",
                field = "trainer",
                message = "Wrong trainer field"
            )
        )
    }
}
