package ru.khrebtov.do_yoga

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.khrebtov.do_yoga.common.models.DoYogaClass
import ru.khrebtov.do_yoga.common.models.DoYogaClassId
import ru.khrebtov.do_yoga.common.repo.DbClassIdRequest
import ru.khrebtov.do_yoga.common.repo.IClassRepository


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoClassReadTest {
    abstract val repo: IClassRepository
    protected open val readSucc = initObjects[0]

    @Test
    fun readSuccess() = runRepoTest {
        val result = repo.readClass(DbClassIdRequest(readSucc.id))

        assertEquals(true, result.isSuccess)
        assertEquals(readSucc.copy(time = result.data?.time), result.data)
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun readNotFound() = runRepoTest {
        val result = repo.readClass(DbClassIdRequest(notFoundId))

        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }

    companion object : BaseInitClasses("delete") {
        override val initObjects: List<DoYogaClass> = listOf(
            createInitTestModel("read")
        )

        val notFoundId = DoYogaClassId("ad-repo-read-notFound")

    }
}
