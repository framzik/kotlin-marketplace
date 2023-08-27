package ru.khrebtov.do_yoga.common.repo

import ru.khrebtov.do_yoga.common.helpers.errorRepoConcurrency
import ru.khrebtov.do_yoga.common.models.DoYogaClass
import ru.khrebtov.do_yoga.common.models.DoYogaClassLock
import ru.khrebtov.do_yoga.common.models.DoYogaError

data class DbClassResponse(
    override val data: DoYogaClass?,
    override val isSuccess: Boolean,
    override val errors: List<DoYogaError> = emptyList()
) : IDbResponse<DoYogaClass> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbClassResponse(null, true)
        fun success(result: DoYogaClass) = DbClassResponse(result, true)
        fun error(errors: List<DoYogaError>, data: DoYogaClass? = null) = DbClassResponse(data, false, errors)
        fun error(error: DoYogaError, data: DoYogaClass? = null) = DbClassResponse(data, false, listOf(error))

        val errorEmptyId = error(ru.khrebtov.do_yoga.common.helpers.errorEmptyId)

        fun errorConcurrent(lock: DoYogaClassLock, ad: DoYogaClass?) = error(
            errorRepoConcurrency(lock, ad?.lock?.let { DoYogaClassLock(it.asString()) }),
            ad
        )

        val errorNotFound = error(ru.khrebtov.do_yoga.common.helpers.errorNotFound)
    }
}
