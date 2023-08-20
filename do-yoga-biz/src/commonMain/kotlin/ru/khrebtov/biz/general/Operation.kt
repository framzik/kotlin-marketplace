package ru.khrebtov.biz.general

import ru.khrebtov.cor.ICorChainDsl
import ru.khrebtov.cor.chain
import ru.khrebtov.do_yoga.common.DoYogaContext
import ru.khrebtov.do_yoga.common.models.DoYogaCommand
import ru.khrebtov.do_yoga.common.models.DoYogaState

fun ICorChainDsl<DoYogaContext>.operation(
    title: String,
    command: DoYogaCommand,
    block: ICorChainDsl<DoYogaContext>.() -> Unit
) = chain {
    block()
    this.title = title
    on { this.command == command && state == DoYogaState.RUNNING }
}
