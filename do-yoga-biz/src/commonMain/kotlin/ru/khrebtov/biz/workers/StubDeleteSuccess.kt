package ru.khrebtov.biz.workers

import ru.khrebtov.cor.ICorChainDsl
import ru.khrebtov.cor.worker
import ru.otus.otuskotlin.marketplace.common.DoYogaContext
import ru.otus.otuskotlin.marketplace.common.models.DoYogaState
import ru.otus.otuskotlin.marketplace.common.stubs.DoYogaStubs
import ru.otus.otuskotlin.marketplace.stubs.DoYogaClassStub

fun ICorChainDsl<DoYogaContext>.stubDeleteSuccess(title: String) = worker {
    this.title = title
    on { stubCase == DoYogaStubs.SUCCESS && state == DoYogaState.RUNNING }
    handle {
        state = DoYogaState.FINISHING
        val stub = DoYogaClassStub.prepareResult {
            classRequest.officeAddress.takeIf { !it.isNullOrBlank() }?.also { this.officeAddress = it }
        }
        classResponse = stub
    }
}
