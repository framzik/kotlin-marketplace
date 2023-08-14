package ru.otus.otuskotlin.marketplace.common.helpers

import ru.otus.otuskotlin.marketplace.common.DoYogaContext
import ru.otus.otuskotlin.marketplace.common.models.DoYogaError
import ru.otus.otuskotlin.marketplace.common.models.DoYogaState

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
fun DoYogaContext.fail(error: DoYogaError) {
    addError(error)
    state = DoYogaState.FAILING
}

fun errorValidation(
    field: String,
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, badSymbols, tooLong, etc
     */
    violationCode: String,
    description: String,
    level: DoYogaError.Level = DoYogaError.Level.ERROR,
) = DoYogaError(
    code = "validation-$field-$violationCode",
    field = field,
    group = "validation",
    message = "Validation error for field $field: $description",
    level = level,
)

fun errorAdministration(
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, badSymbols, tooLong, etc
     */
    field: String = "",
    violationCode: String,
    description: String,
    level: DoYogaError.Level = DoYogaError.Level.ERROR,
) = DoYogaError(
    field = field,
    code = "administration-$violationCode",
    group = "administration",
    message = "Microservice management error: $description",
    level = level,
)