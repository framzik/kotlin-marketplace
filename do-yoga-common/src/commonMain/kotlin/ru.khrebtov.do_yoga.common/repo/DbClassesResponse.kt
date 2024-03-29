package ru.khrebtov.do_yoga.common.repo

import ru.khrebtov.do_yoga.common.models.DoYogaClass
import ru.khrebtov.do_yoga.common.models.DoYogaError

data class DbClassesResponse(
    override val data: List<DoYogaClass>?,
    override val isSuccess: Boolean,
    override val errors: List<DoYogaError> = emptyList(),
) : IDbResponse<List<DoYogaClass>> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbClassesResponse(emptyList(), true)
        fun success(result: List<DoYogaClass>) = DbClassesResponse(result, true)
        fun error(errors: List<DoYogaError>) = DbClassesResponse(null, false, errors)
        fun error(error: DoYogaError) = DbClassesResponse(null, false, listOf(error))
    }
}
