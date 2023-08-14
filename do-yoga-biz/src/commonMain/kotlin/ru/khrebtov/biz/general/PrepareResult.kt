package ru.khrebtov.biz.general

import ru.khrebtov.cor.ICorChainDsl
import ru.khrebtov.cor.worker
import ru.otus.otuskotlin.marketplace.common.DoYogaContext
import ru.otus.otuskotlin.marketplace.common.models.DoYogaState
import ru.otus.otuskotlin.marketplace.common.models.DoYogaWorkMode

fun ICorChainDsl<DoYogaContext>.prepareResult(title: String) = worker {
    this.title = title
    description = "Подготовка данных для ответа клиенту на запрос"
    on { workMode != DoYogaWorkMode.STUB }
    handle {
        classResponse = classRepoDone
        classesResponse = classesRepoDone
        state = when (val st = state) {
            DoYogaState.RUNNING -> DoYogaState.FINISHING
            else -> st
        }
    }
}
