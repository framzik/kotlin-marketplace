package ru.khrebtov.biz.repo

import ru.khrebtov.cor.ICorChainDsl
import ru.khrebtov.cor.worker
import ru.otus.otuskotlin.marketplace.common.DoYogaContext
import ru.otus.otuskotlin.marketplace.common.models.DoYogaState

fun ICorChainDsl<DoYogaContext>.repoPrepareCreate(title: String) = worker {
    this.title = title
    description = "Подготовка объекта к сохранению в базе данных"
    on { state == DoYogaState.RUNNING }
    handle {
        classRepoRead = classValidated.deepCopy()
        classRepoPrepare = classRepoRead
    }
}
