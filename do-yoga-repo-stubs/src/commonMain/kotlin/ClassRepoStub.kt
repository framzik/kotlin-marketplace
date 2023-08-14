package ru.khrebtov.backend.repository.inmemory

import ru.otus.otuskotlin.marketplace.common.models.DoYogaType
import ru.otus.otuskotlin.marketplace.common.repo.DbClassFilterRequest
import ru.otus.otuskotlin.marketplace.common.repo.DbClassIdRequest
import ru.otus.otuskotlin.marketplace.common.repo.DbClassRequest
import ru.otus.otuskotlin.marketplace.common.repo.DbClassResponse
import ru.otus.otuskotlin.marketplace.common.repo.DbClassesResponse
import ru.otus.otuskotlin.marketplace.common.repo.IClassRepository
import ru.otus.otuskotlin.marketplace.stubs.DoYogaClassStub

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
