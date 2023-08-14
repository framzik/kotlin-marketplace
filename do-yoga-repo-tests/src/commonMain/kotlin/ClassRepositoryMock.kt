package ru.otus.otuskotlin.marketplace.backend.repo.tests

import ru.otus.otuskotlin.marketplace.common.repo.*

class ClassRepositoryMock(
    private val invokeCreateClass: (DbClassRequest) -> DbClassResponse = { DbClassResponse.MOCK_SUCCESS_EMPTY },
    private val invokeReadClass: (DbClassIdRequest) -> DbClassResponse = { DbClassResponse.MOCK_SUCCESS_EMPTY },
    private val invokeUpdateClass: (DbClassRequest) -> DbClassResponse = { DbClassResponse.MOCK_SUCCESS_EMPTY },
    private val invokeDeleteClass: (DbClassIdRequest) -> DbClassResponse = { DbClassResponse.MOCK_SUCCESS_EMPTY },
    private val invokeSearchClass: (DbClassFilterRequest) -> DbClassesResponse = { DbClassesResponse.MOCK_SUCCESS_EMPTY },
): IClassRepository {
    override suspend fun createClass(rq: DbClassRequest): DbClassResponse {
        return invokeCreateClass(rq)
    }

    override suspend fun readClass(rq: DbClassIdRequest): DbClassResponse {
        return invokeReadClass(rq)
    }

    override suspend fun updateClass(rq: DbClassRequest): DbClassResponse {
        return invokeUpdateClass(rq)
    }

    override suspend fun deleteClass(rq: DbClassIdRequest): DbClassResponse {
        return invokeDeleteClass(rq)
    }

    override suspend fun searchClass(rq: DbClassFilterRequest): DbClassesResponse {
        return invokeSearchClass(rq)
    }
}
