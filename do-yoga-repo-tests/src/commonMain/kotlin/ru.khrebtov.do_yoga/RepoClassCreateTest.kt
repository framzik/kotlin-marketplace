package ru.khrebtov.do_yoga

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.khrebtov.do_yoga.common.models.DoYogaClass
import ru.khrebtov.do_yoga.common.models.DoYogaClassId
import ru.khrebtov.do_yoga.common.models.DoYogaClassLock
import ru.khrebtov.do_yoga.common.models.DoYogaType
import ru.khrebtov.do_yoga.common.models.DoYogaVisibility
import ru.khrebtov.do_yoga.common.repo.DbClassRequest
import ru.khrebtov.do_yoga.common.repo.IClassRepository


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoClassCreateTest {
    abstract val repo: IClassRepository
    protected open val lockNew: DoYogaClassLock = DoYogaClassLock("20000000-0000-0000-0000-000000000002")

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
        assertEquals(lockNew, result.data?.lock)
    }

    companion object : BaseInitClasses("create") {
        override val initObjects: List<DoYogaClass> = emptyList()
    }
}
