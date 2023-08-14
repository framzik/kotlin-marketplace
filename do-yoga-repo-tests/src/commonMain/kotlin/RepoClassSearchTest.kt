package ru.otus.otuskotlin.marketplace.backend.repo.tests

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.otus.otuskotlin.marketplace.common.models.DoYogaClass
import ru.otus.otuskotlin.marketplace.common.models.DoYogaType
import ru.otus.otuskotlin.marketplace.common.models.DoYogaUserId
import ru.otus.otuskotlin.marketplace.common.repo.DbClassFilterRequest
import ru.otus.otuskotlin.marketplace.common.repo.IClassRepository


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoClassSearchTest {
    abstract val repo: IClassRepository

    protected open val initializedObjects: List<DoYogaClass> = initObjects

    @Test
    fun searchTrainer() = runRepoTest {
        val result = repo.searchClass(DbClassFilterRequest(trainer = searchTrainerId.asString()))
        assertEquals(true, result.isSuccess)
        val expected = listOf(initializedObjects[1]).sortedBy { it.id.asString() }
        assertEquals(expected, result.data)
        assertEquals(emptyList(), result.errors)
    }

    companion object : BaseInitClasses("search") {

        val searchTrainerId = DoYogaUserId("owner-124")
        override val initObjects: List<DoYogaClass> = listOf(
            createInitTestModel("class1"),
            createInitTestModel("class2", trainer = searchTrainerId.asString()),
            createInitTestModel("class3", classType = DoYogaType.GROUP),
        )
    }
}
