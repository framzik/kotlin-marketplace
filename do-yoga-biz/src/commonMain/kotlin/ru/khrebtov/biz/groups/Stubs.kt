package ru.khrebtov.biz.groups

import ru.khrebtov.cor.ICorChainDsl
import ru.khrebtov.cor.chain
import ru.otus.otuskotlin.marketplace.common.DoYogaContext
import ru.otus.otuskotlin.marketplace.common.models.DoYogaState
import ru.otus.otuskotlin.marketplace.common.models.DoYogaWorkMode

fun ICorChainDsl<DoYogaContext>.stubs(title: String, block: ICorChainDsl<DoYogaContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { workMode == DoYogaWorkMode.STUB && state == DoYogaState.RUNNING }
}
