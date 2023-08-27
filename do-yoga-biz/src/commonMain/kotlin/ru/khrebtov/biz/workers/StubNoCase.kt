package ru.khrebtov.biz.workers

import ru.khrebtov.cor.ICorChainDsl
import ru.khrebtov.cor.worker
import ru.khrebtov.do_yoga.common.DoYogaContext
import ru.khrebtov.do_yoga.common.helpers.fail
import ru.khrebtov.do_yoga.common.models.DoYogaError
import ru.khrebtov.do_yoga.common.models.DoYogaState

fun ICorChainDsl<DoYogaContext>.stubNoCase(title: String) = worker {
    this.title = title
    on { state == DoYogaState.RUNNING }
    handle {
        fail(
            DoYogaError(
                code = "validation",
                field = "stub",
                group = "validation",
                message = "Wrong stub case is requested: ${stubCase.name}"
            )
        )
    }
}
