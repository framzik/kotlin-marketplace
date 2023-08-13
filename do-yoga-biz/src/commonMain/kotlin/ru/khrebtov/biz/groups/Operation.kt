package ru.khrebtov.biz.groups

import ru.khrebtov.cor.ICorChainDsl
import ru.khrebtov.cor.chain
import ru.otus.otuskotlin.marketplace.common.DoYogaContext
import ru.otus.otuskotlin.marketplace.common.models.DoYogaCommand
import ru.otus.otuskotlin.marketplace.common.models.DoYogaState

fun ICorChainDsl<DoYogaContext>.operation(
    title: String,
    command: DoYogaCommand,
    block: ICorChainDsl<DoYogaContext>.() -> Unit
) = chain {
    block()
    this.title = title
    on { this.command == command && state == DoYogaState.RUNNING }
}
