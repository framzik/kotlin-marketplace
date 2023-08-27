package ru.khrebtov.do_yoga.common.helpers

import ru.khrebtov.do_yoga.common.DoYogaContext
import ru.khrebtov.do_yoga.common.exceptions.RepoConcurrencyException
import ru.khrebtov.do_yoga.common.models.DoYogaClassLock
import ru.khrebtov.do_yoga.common.models.DoYogaError

fun Throwable.asDoYogaError(
    code: String = "unknown",
    group: String = "ru.khrebtov.do_yoga.common/exceptionsv.do_yoga.common/exceptions",
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
    state = ru.khrebtov.do_yoga.common.models.DoYogaState.FAILING
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
    exception: Exception? = null,
    level: DoYogaError.Level = DoYogaError.Level.ERROR,
) = DoYogaError(
    field = field,
    code = "administration-$violationCode",
    group = "administration",
    message = "Microservice management error: $description",
    level = level,
    exception = exception,
)

fun errorRepoConcurrency(
    expectedLock: DoYogaClassLock,
    actualLock: DoYogaClassLock?,
    exception: Exception? = null,
) = DoYogaError(
    field = "lock",
    code = "concurrency",
    group = "ru.khrebtov.do_yoga.common/repohrebtov.do_yoga.common/repo",
    message = "The object has been changed concurrently by another user or process",
    exception = exception ?: RepoConcurrencyException(expectedLock, actualLock),
)

val errorNotFound = DoYogaError(
    field = "id",
    message = "Not Found",
    code = "not-found"
)

val errorEmptyId = DoYogaError(
    field = "id",
    message = "Id must not be null or blank"
)