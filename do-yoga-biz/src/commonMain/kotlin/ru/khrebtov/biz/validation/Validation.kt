package ru.khrebtov.biz.validation

import ru.khrebtov.cor.ICorChainDsl
import ru.khrebtov.cor.chain
import ru.otus.otuskotlin.marketplace.common.DoYogaContext
import ru.otus.otuskotlin.marketplace.common.models.DoYogaState

fun ICorChainDsl<DoYogaContext>.validation(block: ICorChainDsl<DoYogaContext>.() -> Unit) = chain {
    block()
    title = "Валидация"

    on { state == DoYogaState.RUNNING }
}
