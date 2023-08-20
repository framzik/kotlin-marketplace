package ru.khrebtov.biz.validation

import ru.khrebtov.cor.ICorChainDsl
import ru.khrebtov.cor.worker
import ru.khrebtov.do_yoga.common.DoYogaContext
import ru.khrebtov.do_yoga.common.helpers.errorValidation
import ru.khrebtov.do_yoga.common.helpers.fail
import ru.khrebtov.do_yoga.common.models.DoYogaClassLock

fun ICorChainDsl<DoYogaContext>.validateLockProperFormat(title: String) = worker {
    this.title = title

    // Может быть вынесен в MkplAdId для реализации различных форматов
    val regExp = Regex("^[0-9a-zA-Z-]+$")
    on { classValidating.lock != DoYogaClassLock.NONE && !classValidating.lock.asString().matches(regExp) }
    handle {
        val encodedId = classValidating.lock.asString()
        fail(
            errorValidation(
                field = "lock",
                violationCode = "badFormat",
                description = "value $encodedId must contain only"
            )
        )
    }
}
