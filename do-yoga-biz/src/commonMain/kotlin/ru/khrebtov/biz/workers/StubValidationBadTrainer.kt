package ru.khrebtov.biz.workers

import ru.khrebtov.cor.ICorChainDsl
import ru.khrebtov.cor.worker
import ru.otus.otuskotlin.marketplace.common.DoYogaContext
import ru.otus.otuskotlin.marketplace.common.models.DoYogaError
import ru.otus.otuskotlin.marketplace.common.models.DoYogaState
import ru.otus.otuskotlin.marketplace.common.stubs.DoYogaStubs

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
