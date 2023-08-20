package ru.khrebtov.biz.workers

import ru.khrebtov.cor.ICorChainDsl
import ru.khrebtov.cor.worker
import ru.khrebtov.do_yoga.common.DoYogaContext
import ru.khrebtov.do_yoga.common.models.DoYogaState
import ru.khrebtov.do_yoga.common.models.DoYogaType
import ru.khrebtov.do_yoga.common.models.DoYogaVisibility
import ru.khrebtov.do_yoga.common.stubs.DoYogaStubs
import ru.khrebtov.do_yoga.DoYogaClassStub

fun ICorChainDsl<DoYogaContext>.stubUpdateSuccess(title: String) = worker {
    this.title = title
    on { stubCase == DoYogaStubs.SUCCESS && state == DoYogaState.RUNNING }
    handle {
        state = DoYogaState.FINISHING
        val stub = DoYogaClassStub.prepareResult {
            classRequest.id.takeIf { it != ru.khrebtov.do_yoga.common.models.DoYogaClassId.NONE }?.also { this.id = it }
            classRequest.officeAddress.takeIf { !it.isNullOrBlank() }?.also { this.officeAddress = it }
            classRequest.classType.takeIf { it != DoYogaType.NONE }?.also { this.classType = it }
            classRequest.trainer.takeIf { !it.isNullOrBlank() }?.also { this.trainer = it }
            classRequest.visibility.takeIf { it != DoYogaVisibility.NONE }?.also { this.visibility = it }
        }
        classResponse = stub
    }
}
