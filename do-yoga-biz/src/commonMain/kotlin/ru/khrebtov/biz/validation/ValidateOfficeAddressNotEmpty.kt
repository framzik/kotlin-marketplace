package ru.khrebtov.biz.validation

import ru.khrebtov.cor.ICorChainDsl
import ru.khrebtov.cor.worker
import ru.otus.otuskotlin.marketplace.common.DoYogaContext
import ru.otus.otuskotlin.marketplace.common.helpers.errorValidation
import ru.otus.otuskotlin.marketplace.common.helpers.fail

fun ICorChainDsl<DoYogaContext>.validateOfficeAddressNotEmpty(title: String) = worker {
    this.title = title
    on { classValidating.officeAddress?.isEmpty() ?: false }
    handle {
        fail(
            errorValidation(
                field = "officeAddress",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}