package ru.khrebtov.biz.validation

import ru.khrebtov.cor.ICorChainDsl
import ru.khrebtov.cor.worker
import ru.otus.otuskotlin.marketplace.common.DoYogaContext
import ru.otus.otuskotlin.marketplace.common.helpers.errorValidation
import ru.otus.otuskotlin.marketplace.common.helpers.fail


// TODO-validation-7: пример обработки ошибки в рамках бизнес-цепочки
fun ICorChainDsl<DoYogaContext>.validateTrainerHasContent(title: String) = worker {
    this.title = title
    val regExp = Regex("\\p{L}")
    on { classValidating.trainer?.isNotEmpty() ?: false && !(classValidating.trainer?.contains(regExp) ?: true) }
    handle {
        fail(
            errorValidation(
                field = "trainer",
                violationCode = "noContent",
                description = "field must contain letters"
            )
        )
    }
}
