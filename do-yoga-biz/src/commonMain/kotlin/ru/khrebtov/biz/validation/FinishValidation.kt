package ru.khrebtov.biz.validation

import ru.khrebtov.cor.ICorChainDsl
import ru.khrebtov.cor.worker
import ru.otus.otuskotlin.marketplace.common.DoYogaContext
import ru.otus.otuskotlin.marketplace.common.models.DoYogaState

fun ICorChainDsl<DoYogaContext>.finishClassValidation(title: String) = worker {
    this.title = title
    on { state == DoYogaState.RUNNING }
    handle {
        classValidated = classValidating
    }
}

fun ICorChainDsl<DoYogaContext>.finishClassFilterValidation(title: String) = worker {
    this.title = title
    on { state == DoYogaState.RUNNING }
    handle {
        classFilterValidated = classFilterValidating
    }
}
