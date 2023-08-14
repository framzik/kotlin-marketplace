package ru.otus.otuskotlin.marketplace.common.repo

interface IClassRepository {
    suspend fun createClass(rq: DbClassRequest): DbClassResponse
    suspend fun readClass(rq: DbClassIdRequest): DbClassResponse
    suspend fun updateClass(rq: DbClassRequest): DbClassResponse
    suspend fun deleteClass(rq: DbClassIdRequest): DbClassResponse
    suspend fun searchClass(rq: DbClassFilterRequest): DbClassesResponse
    companion object {
        val NONE = object : IClassRepository {
            override suspend fun createClass(rq: DbClassRequest): DbClassResponse {
                TODO("Not yet implemented")
            }

            override suspend fun readClass(rq: DbClassIdRequest): DbClassResponse {
                TODO("Not yet implemented")
            }

            override suspend fun updateClass(rq: DbClassRequest): DbClassResponse {
                TODO("Not yet implemented")
            }

            override suspend fun deleteClass(rq: DbClassIdRequest): DbClassResponse {
                TODO("Not yet implemented")
            }

            override suspend fun searchClass(rq: DbClassFilterRequest): DbClassesResponse {
                TODO("Not yet implemented")
            }
        }
    }
}
