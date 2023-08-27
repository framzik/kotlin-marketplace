package ru.khrebtov.do_yoga.common.repo

import ru.khrebtov.do_yoga.common.models.DoYogaError

interface IDbResponse<T> {
    val data: T?
    val isSuccess: Boolean
    val errors: List<DoYogaError>
}
