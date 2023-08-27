package ru.khrebtov.biz.workers

import ru.khrebtov.cor.ICorChainDsl
import ru.khrebtov.cor.worker
import ru.khrebtov.do_yoga.common.DoYogaContext
import ru.khrebtov.do_yoga.common.models.DoYogaState
import ru.khrebtov.do_yoga.common.models.DoYogaType
import ru.khrebtov.do_yoga.common.stubs.DoYogaStubs
import ru.khrebtov.do_yoga.DoYogaClassStub

fun ICorChainDsl<DoYogaContext>.stubSignUpSuccess(title: String) = worker {
    this.title = title
    on { stubCase == DoYogaStubs.SUCCESS && state == DoYogaState.RUNNING }
    handle {
        state = DoYogaState.FINISHING
        classResponse = DoYogaClassStub.prepareResult {
            classRequest.id.takeIf { it != ru.khrebtov.do_yoga.common.models.DoYogaClassId.NONE }?.also { this.id = it }
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
