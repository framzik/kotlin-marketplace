package ru.khrebtov.biz.workers

import ru.khrebtov.cor.ICorChainDsl
import ru.khrebtov.cor.worker
import ru.otus.otuskotlin.marketplace.common.DoYogaContext
import ru.otus.otuskotlin.marketplace.common.models.DoYogaError
import ru.otus.otuskotlin.marketplace.common.models.DoYogaState
import ru.otus.otuskotlin.marketplace.common.stubs.DoYogaStubs

fun ICorChainDsl<DoYogaContext>.stubValidationBadOfficeAddress(title: String) = worker {
    this.title = title
    on { stubCase == DoYogaStubs.BAD_OFFICE_ADDRESS && state == DoYogaState.RUNNING }
    handle {
        state = DoYogaState.FAILING
        this.errors.add(
            DoYogaError(
                group = "validation",
                code = "validation-OfficeAddress",
                field = "officeAddress",
                message = "Wrong OfficeAddress field"
            )
        )
    }
}
