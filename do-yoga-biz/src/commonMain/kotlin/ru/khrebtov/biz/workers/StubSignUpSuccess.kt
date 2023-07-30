package ru.khrebtov.biz.workers

import ru.khrebtov.cor.ICorChainDsl
import ru.khrebtov.cor.worker
import ru.otus.otuskotlin.marketplace.common.DoYogaContext
import ru.otus.otuskotlin.marketplace.common.models.DoYogaClassId
import ru.otus.otuskotlin.marketplace.common.models.DoYogaState
import ru.otus.otuskotlin.marketplace.common.models.DoYogaType
import ru.otus.otuskotlin.marketplace.common.stubs.DoYogaStubs
import ru.otus.otuskotlin.marketplace.stubs.DoYogaClassStub

fun ICorChainDsl<DoYogaContext>.stubSignUpSuccess(title: String) = worker {
    this.title = title
    on { stubCase == DoYogaStubs.SUCCESS && state == DoYogaState.RUNNING }
    handle {
        state = DoYogaState.FINISHING
        classResponse = DoYogaClassStub.prepareResult {
            classRequest.id.takeIf { it != DoYogaClassId.NONE }?.also { this.id = it }
        }
        classesResponse.addAll(
            DoYogaClassStub.prepareSignUpList(
                classResponse.officeAddress ?: "",
                DoYogaType.PERSONAL,
                classResponse.trainer ?: ""
            )
        )
    }
}
