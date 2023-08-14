package ru.otus.otuskotlin.marketplace.common.repo

import ru.otus.otuskotlin.marketplace.common.models.DoYogaClass
import ru.otus.otuskotlin.marketplace.common.models.DoYogaError

data class DbClassResponse(
    override val data: DoYogaClass?,
    override val isSuccess: Boolean,
    override val errors: List<DoYogaError> = emptyList()
) : IDbResponse<DoYogaClass> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbClassResponse(null, true)
        fun success(result: DoYogaClass) = DbClassResponse(result, true)
        fun error(errors: List<DoYogaError>) = DbClassResponse(null, false, errors)
        fun error(error: DoYogaError) = DbClassResponse(null, false, listOf(error))
    }
}
