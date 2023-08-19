package ru.khrebtov.repo.inmemory

import com.benasher44.uuid.uuid4
import io.github.reactivecircus.cache4k.Cache
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ru.khrebtov.repo.inmemory.model.ClassEntity
import ru.otus.otuskotlin.marketplace.common.helpers.errorRepoConcurrency
import ru.otus.otuskotlin.marketplace.common.models.DoYogaClass
import ru.otus.otuskotlin.marketplace.common.models.DoYogaClassId
import ru.otus.otuskotlin.marketplace.common.models.DoYogaClassLock
import ru.otus.otuskotlin.marketplace.common.models.DoYogaError
import ru.otus.otuskotlin.marketplace.common.models.DoYogaType
import ru.otus.otuskotlin.marketplace.common.repo.DbClassFilterRequest
import ru.otus.otuskotlin.marketplace.common.repo.DbClassIdRequest
import ru.otus.otuskotlin.marketplace.common.repo.DbClassRequest
import ru.otus.otuskotlin.marketplace.common.repo.DbClassResponse
import ru.otus.otuskotlin.marketplace.common.repo.DbClassesResponse
import ru.otus.otuskotlin.marketplace.common.repo.IClassRepository

class ClassRepoInMemory(
    initObjects: List<DoYogaClass> = emptyList(),
    ttl: Duration = 2.minutes,
    val randomUuid: () -> String = { uuid4().toString() },
) : IClassRepository {

    private val cache = Cache.Builder<String, ClassEntity>()
        .expireAfterWrite(ttl)
        .build()
    private val mutex: Mutex = Mutex()

    init {
        initObjects.forEach {
            save(it)
        }
    }

    private fun save(doYogaClass: DoYogaClass) {
        val entity = ClassEntity(doYogaClass)
        if (entity.id == null) {
            return
        }
        cache.put(entity.id, entity)
    }

    override suspend fun createClass(rq: DbClassRequest): DbClassResponse {
        val key = randomUuid()
        val yogaClass = rq.doYogaClass.copy(id = DoYogaClassId(key), lock = DoYogaClassLock(randomUuid()))
        val entity = ClassEntity(yogaClass)
        cache.put(key, entity)
        return DbClassResponse(
            data = yogaClass,
            isSuccess = true,
        )
    }

    override suspend fun readClass(rq: DbClassIdRequest): DbClassResponse {
        val key = rq.id.takeIf { it != DoYogaClassId.NONE }?.asString() ?: return resultErrorEmptyId
        return cache.get(key)
            ?.let {
                DbClassResponse(
                    data = it.toInternal(),
                    isSuccess = true,
                )
            } ?: resultErrorNotFound
    }

    override suspend fun updateClass(rq: DbClassRequest): DbClassResponse {
        val key = rq.doYogaClass.id.takeIf { it != DoYogaClassId.NONE }?.asString() ?: return resultErrorEmptyId
        val oldLock =
            rq.doYogaClass.lock.takeIf { it != DoYogaClassLock.NONE }?.asString() ?: return resultErrorEmptyLock
        val newClass = rq.doYogaClass.copy(lock = DoYogaClassLock(randomUuid()))
        val entity = ClassEntity(newClass)
        return mutex.withLock {
            val oldClass = cache.get(key)
            when {
                oldClass == null -> resultErrorNotFound
                oldClass.lock != oldLock -> DbClassResponse(
                    data = oldClass.toInternal(),
                    isSuccess = false,
                    errors = listOf(
                        errorRepoConcurrency(
                            DoYogaClassLock(oldLock),
                            oldClass.lock?.let { DoYogaClassLock(it) })
                    )
                )

                else -> {
                    cache.put(key, entity)
                    DbClassResponse(
                        data = newClass,
                        isSuccess = true,
                    )
                }
            }
        }
    }

    override suspend fun deleteClass(rq: DbClassIdRequest): DbClassResponse {
        val key = rq.id.takeIf { it != DoYogaClassId.NONE }?.asString() ?: return resultErrorEmptyId
        val oldLock =
            rq.lock.takeIf { it != DoYogaClassLock.NONE }?.asString() ?: return resultErrorEmptyLock

        return mutex.withLock {
            val oldClass = cache.get(key)
            when {
                oldClass == null -> resultErrorNotFound
                oldClass.lock != oldLock -> DbClassResponse(
                    data = oldClass.toInternal(),
                    isSuccess = false,
                    errors = listOf(
                        errorRepoConcurrency(
                            DoYogaClassLock(oldLock),
                            oldClass.lock?.let { DoYogaClassLock(it) })
                    )
                )

                else -> {
                    cache.invalidate(key)
                    DbClassResponse(
                        data = oldClass.toInternal(),
                        isSuccess = true,
                    )
                }
            }
        }
    }

    /**
     * Поиск классов по фильтру
     * Если в фильтре не установлен какой-либо из параметров - по нему фильтрация не идет
     */
    override suspend fun searchClass(rq: DbClassFilterRequest): DbClassesResponse {
        val result = cache.asMap().asSequence()
            .filter { entry ->
                rq.searchFilter.takeIf { it.isNotBlank() }?.let {
                    entry.value.officeAddress?.contains(it) ?: false
                } ?: true
            }
            .filter { entry ->
                rq.classType.takeIf { it != DoYogaType.NONE }?.let {
                    it.name == entry.value.classType
                } ?: true
            }
            .filter { entry ->
                rq.trainer.takeIf { it.isNotBlank() }?.let {
                    entry.value.trainer?.contains(it) ?: false
                } ?: true
            }
            .map { it.value.toInternal() }
            .toList()
        return DbClassesResponse(
            data = result,
            isSuccess = true
        )
    }

    companion object {
        val resultErrorEmptyId = DbClassResponse(
            data = null,
            isSuccess = false,
            errors = listOf(
                DoYogaError(
                    code = "id-empty",
                    group = "validation",
                    field = "id",
                    message = "Id must not be null or blank"
                )
            )
        )
        val resultErrorEmptyLock = DbClassResponse(
            data = null,
            isSuccess = false,
            errors = listOf(
                DoYogaError(
                    code = "lock-empty",
                    group = "validation",
                    field = "lock",
                    message = "Lock must not be null or blank"
                )
            )
        )
        val resultErrorNotFound = DbClassResponse(
            isSuccess = false,
            data = null,
            errors = listOf(
                DoYogaError(
                    code = "not-found",
                    field = "id",
                    message = "Not Found"
                )
            )
        )
    }
}
