package ru.otus.otuskotlin.marketplace.common.helpers

import ru.otus.otuskotlin.marketplace.common.DoYogaContext
import ru.otus.otuskotlin.marketplace.common.models.DoYogaError

fun Throwable.asDoYogaError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = DoYogaError(
    code = code,
    group = group,
    field = "",
    message = message,
    exception = this,
)

fun DoYogaContext.addError(vararg error: DoYogaError) = errors.addAll(error)
