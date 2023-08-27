package ru.khrebtov.biz.repo

import ru.khrebtov.cor.ICorChainDsl
import ru.khrebtov.cor.worker
import ru.khrebtov.do_yoga.common.DoYogaContext
import ru.khrebtov.do_yoga.common.models.DoYogaState
import ru.khrebtov.do_yoga.common.repo.DbClassFilterRequest

fun ICorChainDsl<DoYogaContext>.repoSearch(title: String) = worker {
    this.title = title
    description = "Поиск класса в БД по фильтру"
    on { state == DoYogaState.RUNNING }
    handle {
        val request = DbClassFilterRequest(
            searchFilter = classFilterValidated.searchString,
            trainer = classFilterValidated.trainer,
            classType = classFilterValidated.dealType,
        )
        val result = classRepo.searchClass(request)
        val resultClasses = result.data
        if (result.isSuccess && resultClasses != null) {
            classesRepoDone = resultClasses.toMutableList()
        } else {
            state = DoYogaState.FAILING
            errors.addAll(result.errors)
        }
    }
}
