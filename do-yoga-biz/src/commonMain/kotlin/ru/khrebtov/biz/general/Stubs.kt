package ru.khrebtov.biz.general

import ru.khrebtov.cor.ICorChainDsl
import ru.khrebtov.cor.chain
import ru.khrebtov.do_yoga.common.DoYogaContext
import ru.khrebtov.do_yoga.common.models.DoYogaState
import ru.khrebtov.do_yoga.common.models.DoYogaWorkMode

fun ICorChainDsl<DoYogaContext>.stubs(title: String, block: ICorChainDsl<DoYogaContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { workMode == DoYogaWorkMode.STUB && state == DoYogaState.RUNNING }
}
