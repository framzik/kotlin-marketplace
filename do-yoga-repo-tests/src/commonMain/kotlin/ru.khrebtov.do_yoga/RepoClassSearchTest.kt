package ru.khrebtov.do_yoga

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.khrebtov.do_yoga.common.models.DoYogaClass
import ru.khrebtov.do_yoga.common.models.DoYogaType
import ru.khrebtov.do_yoga.common.models.DoYogaUserId
import ru.khrebtov.do_yoga.common.repo.DbClassFilterRequest
import ru.khrebtov.do_yoga.common.repo.IClassRepository


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoClassSearchTest {
    abstract val repo: IClassRepository

    protected open val initializedObjects: List<DoYogaClass> = initObjects

    @Test
    fun searchTrainer() = runRepoTest {
        val result = repo.searchClass(DbClassFilterRequest(trainer = searchTrainerId.asString()))
        assertEquals(true, result.isSuccess)
        val expected =
            listOf(initializedObjects[1].copy(time = result.data?.get(0)?.time)).sortedBy { it.id.asString() }
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
