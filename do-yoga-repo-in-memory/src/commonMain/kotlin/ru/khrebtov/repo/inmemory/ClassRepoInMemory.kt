package ru.khrebtov.repo.inmemory

import com.benasher44.uuid.uuid4
import io.github.reactivecircus.cache4k.Cache
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlinx.coroutines.sync.Mutex
import ru.khrebtov.repo.inmemory.model.ClassEntity
import ru.otus.otuskotlin.marketplace.common.models.DoYogaClass
import ru.otus.otuskotlin.marketplace.common.models.DoYogaClassId
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
        val ad = rq.doYogaClass.copy(id = DoYogaClassId(key))
        val entity = ClassEntity(ad)
        cache.put(key, entity)
        return DbClassResponse(
            data = ad,
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
        val newClass = rq.doYogaClass.copy()
        val entity = ClassEntity(newClass)
        return when (cache.get(key)) {
            null -> resultErrorNotFound
            else -> {
                cache.put(key, entity)
                DbClassResponse(
                    data = newClass,
                    isSuccess = true,
                )
            }
        }
    }

    override suspend fun deleteClass(rq: DbClassIdRequest): DbClassResponse {
        val key = rq.id.takeIf { it != DoYogaClassId.NONE }?.asString() ?: return resultErrorEmptyId
        return when (val oldClass = cache.get(key)) {
            null -> resultErrorNotFound
            else -> {
                cache.invalidate(key)
                DbClassResponse(
                    data = oldClass.toInternal(),
                    isSuccess = true,
                )
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
