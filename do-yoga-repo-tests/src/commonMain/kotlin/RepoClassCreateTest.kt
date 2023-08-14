package ru.otus.otuskotlin.marketplace.backend.repo.tests

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.otus.otuskotlin.marketplace.common.models.*
import ru.otus.otuskotlin.marketplace.common.repo.DbClassRequest
import ru.otus.otuskotlin.marketplace.common.repo.IClassRepository


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoClassCreateTest {
    abstract val repo: IClassRepository

    private val createObj = DoYogaClass(
        officeAddress = "Spot 18",
        trainer = "Shwarch",
        visibility = DoYogaVisibility.VISIBLE_TO_GROUP,
        classType = DoYogaType.GROUP,
    )

    @Test
    fun createSuccess() = runRepoTest {
        val result = repo.createClass(DbClassRequest(createObj))
        val expected = createObj.copy(id = result.data?.id ?: DoYogaClassId.NONE)
        assertEquals(true, result.isSuccess)
        assertEquals(expected.officeAddress, result.data?.officeAddress)
        assertEquals(expected.trainer, result.data?.trainer)
        assertEquals(expected.classType, result.data?.classType)
        assertNotEquals(DoYogaClassId.NONE, result.data?.id)
        assertEquals(emptyList(), result.errors)
    }

    companion object : BaseInitClasses("create") {
        override val initObjects: List<DoYogaClass> = emptyList()
    }
}
