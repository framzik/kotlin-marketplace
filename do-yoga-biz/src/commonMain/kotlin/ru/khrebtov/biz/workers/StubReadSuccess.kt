package ru.khrebtov.biz.workers

import ru.khrebtov.cor.ICorChainDsl
import ru.khrebtov.cor.worker
import ru.khrebtov.do_yoga.common.DoYogaContext
import ru.khrebtov.do_yoga.common.models.DoYogaState
import ru.khrebtov.do_yoga.common.stubs.DoYogaStubs
import ru.khrebtov.do_yoga.DoYogaClassStub

fun ICorChainDsl<DoYogaContext>.stubReadSuccess(title: String) = worker {
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
