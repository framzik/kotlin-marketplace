package ru.khrebtov.biz.workers

import ru.khrebtov.cor.ICorChainDsl
import ru.khrebtov.cor.worker
import ru.otus.otuskotlin.marketplace.common.DoYogaContext
import ru.otus.otuskotlin.marketplace.common.models.DoYogaClassId
import ru.otus.otuskotlin.marketplace.common.models.DoYogaState
import ru.otus.otuskotlin.marketplace.common.models.DoYogaType
import ru.otus.otuskotlin.marketplace.common.models.DoYogaVisibility
import ru.otus.otuskotlin.marketplace.common.stubs.DoYogaStubs
import ru.otus.otuskotlin.marketplace.stubs.DoYogaClassStub

fun ICorChainDsl<DoYogaContext>.stubUpdateSuccess(title: String) = worker {
    this.title = title
    on { stubCase == DoYogaStubs.SUCCESS && state == DoYogaState.RUNNING }
    handle {
        state = DoYogaState.FINISHING
        val stub = DoYogaClassStub.prepareResult {
            classRequest.id.takeIf { it != DoYogaClassId.NONE }?.also { this.id = it }
            classRequest.officeAddress.takeIf { !it.isNullOrBlank() }?.also { this.officeAddress = it }
            classRequest.classType.takeIf { it != DoYogaType.NONE }?.also { this.classType = it }
            classRequest.trainer.takeIf { !it.isNullOrBlank() }?.also { this.trainer = it }
            classRequest.visibility.takeIf { it != DoYogaVisibility.NONE }?.also { this.visibility = it }
        }
        classResponse = stub
    }
}
