package ru.khrebtov.biz.repo

import ru.khrebtov.cor.ICorChainDsl
import ru.khrebtov.cor.worker
import ru.khrebtov.do_yoga.common.DoYogaContext
import ru.khrebtov.do_yoga.common.models.DoYogaState

fun ICorChainDsl<DoYogaContext>.repoPrepareUpdate(title: String) = worker {
    this.title = title
    description = "Готовим данные к сохранению в БД: совмещаем данные, прочитанные из БД, " +
            "и данные, полученные от пользователя"
    on { state == DoYogaState.RUNNING }
    handle {
        classRepoPrepare = classRepoRead.deepCopy().apply {
            this.officeAddress = classValidated.officeAddress
            students = classValidated.students
            trainer = classValidated.trainer
            classType = classValidated.classType
            visibility = classValidated.visibility
        }
    }
}
