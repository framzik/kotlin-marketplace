package ru.khrebtov.biz.repo

import ru.khrebtov.cor.ICorChainDsl
import ru.khrebtov.cor.worker
import ru.otus.otuskotlin.marketplace.common.DoYogaContext
import ru.otus.otuskotlin.marketplace.common.models.DoYogaState
import ru.otus.otuskotlin.marketplace.common.repo.DbClassFilterRequest

fun ICorChainDsl<DoYogaContext>.repoSignUp(title: String) = worker {
    this.title = title
    description = "Поиск предложений для классов по адресу"
    on { state == DoYogaState.RUNNING }
    handle {
        val classRequest = classRepoPrepare
        val filter = DbClassFilterRequest(
            searchFilter = classRequest.officeAddress ?: "",
            classType = classRequest.classType
        )
        val dbResponse = classRepo.searchClass(filter)

        val resultAds = dbResponse.data
        when {
            !resultAds.isNullOrEmpty() -> classesRepoDone = resultAds.toMutableList()
            dbResponse.isSuccess -> return@handle
            else -> {
                state = DoYogaState.FAILING
                errors.addAll(dbResponse.errors)
            }
        }
    }
}
