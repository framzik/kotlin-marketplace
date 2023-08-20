package ru.khrebtov.do_yoga

import ru.khrebtov.do_yoga.common.repo.DbClassFilterRequest
import ru.khrebtov.do_yoga.common.repo.DbClassIdRequest
import ru.khrebtov.do_yoga.common.repo.DbClassRequest
import ru.khrebtov.do_yoga.common.repo.DbClassResponse
import ru.khrebtov.do_yoga.common.repo.DbClassesResponse
import ru.khrebtov.do_yoga.common.repo.IClassRepository

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
