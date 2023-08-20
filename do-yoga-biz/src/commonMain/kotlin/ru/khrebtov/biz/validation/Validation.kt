package ru.khrebtov.biz.validation

import ru.khrebtov.cor.ICorChainDsl
import ru.khrebtov.cor.chain
import ru.khrebtov.do_yoga.common.DoYogaContext
import ru.khrebtov.do_yoga.common.models.DoYogaState

fun ICorChainDsl<DoYogaContext>.validation(block: ICorChainDsl<DoYogaContext>.() -> Unit) = chain {
    block()
    title = "Валидация"

    on { state == DoYogaState.RUNNING }
}
