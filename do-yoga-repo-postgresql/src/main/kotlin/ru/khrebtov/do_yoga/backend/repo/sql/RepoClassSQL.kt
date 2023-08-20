package ru.khrebtov.do_yoga.backend.repo.sql

import com.benasher44.uuid.uuid4
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import ru.khrebtov.do_yoga.common.helpers.asDoYogaError
import ru.khrebtov.do_yoga.common.models.DoYogaClass
import ru.khrebtov.do_yoga.common.models.DoYogaClassId
import ru.khrebtov.do_yoga.common.models.DoYogaClassLock
import ru.khrebtov.do_yoga.common.models.DoYogaType
import ru.khrebtov.do_yoga.common.repo.DbClassFilterRequest
import ru.khrebtov.do_yoga.common.repo.DbClassIdRequest
import ru.khrebtov.do_yoga.common.repo.DbClassRequest
import ru.khrebtov.do_yoga.common.repo.DbClassResponse
import ru.khrebtov.do_yoga.common.repo.DbClassesResponse
import ru.khrebtov.do_yoga.common.repo.IClassRepository


class RepoClassSQL(
    properties: SqlProperties,
    initObjects: Collection<DoYogaClass> = emptyList(),
    val randomUuid: () -> String = { uuid4().toString() },
) : IClassRepository {

    init {
        val driver = when {
            properties.url.startsWith("jdbc:postgresql://") -> "org.postgresql.Driver"
            else -> throw IllegalArgumentException("Unknown driver for url ${properties.url}")
        }

        Database.connect(
            properties.url, driver, properties.user, properties.password
        )

        transaction {
            if (properties.dropDatabase) SchemaUtils.drop(ClassTable)
            SchemaUtils.create(ClassTable)
            initObjects.forEach { createClass(it) }
        }
    }

    private fun createClass(ad: DoYogaClass): DoYogaClass {
        val res = ClassTable.insert {
            to(it, ad, randomUuid)
        }

        return ClassTable.from(res)
    }

    private fun <T> transactionWrapper(block: () -> T, handle: (Exception) -> T): T =
        try {
            transaction {
                block()
            }
        } catch (e: Exception) {
            handle(e)
        }

    private fun transactionWrapper(block: () -> DbClassResponse): DbClassResponse =
        transactionWrapper(block) { DbClassResponse.error(it.asDoYogaError()) }

    override suspend fun createClass(rq: DbClassRequest): DbClassResponse = transactionWrapper {
        DbClassResponse.success(createClass(rq.doYogaClass))
    }

    private fun read(id: DoYogaClassId): DbClassResponse {
        val res = ClassTable.select {
            ClassTable.id eq id.asString()
        }.singleOrNull() ?: return DbClassResponse.errorNotFound
        return DbClassResponse.success(ClassTable.from(res))
    }

    override suspend fun readClass(rq: DbClassIdRequest): DbClassResponse = transactionWrapper { read(rq.id) }

    private fun update(
        id: DoYogaClassId,
        lock: DoYogaClassLock,
        block: (DoYogaClass) -> DbClassResponse
    ): DbClassResponse =
        transactionWrapper {
            if (id == DoYogaClassId.NONE) return@transactionWrapper DbClassResponse.errorEmptyId

            val current = ClassTable.select { ClassTable.id eq id.asString() }
                .firstOrNull()
                ?.let { ClassTable.from(it) }

            when {
                current == null -> DbClassResponse.errorNotFound
                current.lock != lock -> DbClassResponse.errorConcurrent(lock, current)
                else -> block(current)
            }
        }

    override suspend fun updateClass(rq: DbClassRequest): DbClassResponse =
        update(rq.doYogaClass.id, rq.doYogaClass.lock) {
            ClassTable.update({
                (ClassTable.id eq rq.doYogaClass.id.asString()) and (ClassTable.lock eq rq.doYogaClass.lock.asString())
            }) {
                to(it, rq.doYogaClass, randomUuid)
            }
            read(rq.doYogaClass.id)
        }

    override suspend fun deleteClass(rq: DbClassIdRequest): DbClassResponse = update(rq.id, rq.lock) {
        ClassTable.deleteWhere {
            (id eq rq.id.asString()) and (lock eq rq.lock.asString())
        }
        DbClassResponse.success(it)
    }

    override suspend fun searchClass(rq: DbClassFilterRequest): DbClassesResponse =
        transactionWrapper({
            val res = ClassTable.select {
                buildList {
                    add(Op.TRUE)
                    if (rq.trainer.isNotBlank()) {
                        add(ClassTable.trainer eq rq.trainer)
                    }
                    if (rq.classType != DoYogaType.NONE) {
                        add(ClassTable.classType eq rq.classType)
                    }
                    if (rq.searchFilter.isNotBlank()) {
                        add(
                            (ClassTable.officeAddress like "%${rq.searchFilter}%")
                                    or (ClassTable.trainer like "%${rq.searchFilter}%")
                        )
                    }
                }.reduce { a, b -> a and b }
            }
            DbClassesResponse(data = res.map { ClassTable.from(it) }, isSuccess = true)
        }, {
            DbClassesResponse.error(it.asDoYogaError())
        })
}
