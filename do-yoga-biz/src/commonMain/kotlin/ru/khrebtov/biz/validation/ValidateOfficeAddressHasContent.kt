package ru.khrebtov.biz.validation

import ru.khrebtov.cor.ICorChainDsl
import ru.khrebtov.cor.worker
import ru.otus.otuskotlin.marketplace.common.DoYogaContext
import ru.otus.otuskotlin.marketplace.common.helpers.errorValidation
import ru.otus.otuskotlin.marketplace.common.helpers.fail

fun ICorChainDsl<DoYogaContext>.validateOfficeAddressHasContent(title: String) = worker {
    this.title = title
    val regExp = Regex("\\p{L}")
    on {
        classValidating.officeAddress?.isNotEmpty() ?: false && !(classValidating.officeAddress?.contains(regExp)
            ?: true)
    }
    handle {
        fail(
            errorValidation(
                field = "officeAddress",
                violationCode = "noContent",
                description = "field must contain letters"
            )
        )
    }
}
