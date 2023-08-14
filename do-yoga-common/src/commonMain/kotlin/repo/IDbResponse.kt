package ru.otus.otuskotlin.marketplace.common.repo

import ru.otus.otuskotlin.marketplace.common.models.DoYogaError

interface IDbResponse<T> {
    val data: T?
    val isSuccess: Boolean
    val errors: List<DoYogaError>
}
