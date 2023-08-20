package ru.khrebtov.backend.repository.inmemory

import ru.khrebtov.do_yoga.common.models.DoYogaType
import ru.khrebtov.do_yoga.common.repo.DbClassFilterRequest
import ru.khrebtov.do_yoga.common.repo.DbClassIdRequest
import ru.khrebtov.do_yoga.common.repo.DbClassRequest
import ru.khrebtov.do_yoga.common.repo.DbClassResponse
import ru.khrebtov.do_yoga.common.repo.DbClassesResponse
import ru.khrebtov.do_yoga.common.repo.IClassRepository
import ru.khrebtov.do_yoga.DoYogaClassStub

class ClassRepoStub() : IClassRepository {
    override suspend fun createClass(rq: DbClassRequest): DbClassResponse {
        return DbClassResponse(
            data = DoYogaClassStub.prepareResult { },
            isSuccess = true,
        )
    }

    override suspend fun readClass(rq: DbClassIdRequest): DbClassResponse {
        return DbClassResponse(
            data = DoYogaClassStub.prepareResult { },
            isSuccess = true,
        )
    }

    override suspend fun updateClass(rq: DbClassRequest): DbClassResponse {
        return DbClassResponse(
            data = DoYogaClassStub.prepareResult { },
            isSuccess = true,
        )
    }

    override suspend fun deleteClass(rq: DbClassIdRequest): DbClassResponse {
        return DbClassResponse(
            data = DoYogaClassStub.prepareResult { },
            isSuccess = true,
        )
    }

    override suspend fun searchClass(rq: DbClassFilterRequest): DbClassesResponse {
        return DbClassesResponse(
            data = DoYogaClassStub.prepareSearchList(filter = "", DoYogaType.NONE, trainer = ""),
            isSuccess = true,
        )
    }
}
