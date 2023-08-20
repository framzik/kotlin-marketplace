package ru.khrebtov.do_yoga

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.khrebtov.do_yoga.common.models.DoYogaClass
import ru.khrebtov.do_yoga.common.models.DoYogaClassId
import ru.khrebtov.do_yoga.common.models.DoYogaClassLock
import ru.khrebtov.do_yoga.common.models.DoYogaType
import ru.khrebtov.do_yoga.common.models.DoYogaVisibility
import ru.khrebtov.do_yoga.common.repo.DbClassRequest
import ru.khrebtov.do_yoga.common.repo.IClassRepository


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoClassUpdateTest {
    abstract val repo: IClassRepository
    protected open val updateSucc = initObjects[0]
    private val updateIdNotFound = DoYogaClassId("ad-repo-update-not-found")
    protected open val updateConc = initObjects[1]
    protected val lockBad = DoYogaClassLock("20000000-0000-0000-0000-000000000009")
    protected val lockNew = DoYogaClassLock("20000000-0000-0000-0000-000000000002")

    private val reqUpdateSucc by lazy {
        DoYogaClass(
            id = updateSucc.id,
            officeAddress = "Spot 18",
            trainer = "Shwarch",
            visibility = DoYogaVisibility.VISIBLE_TO_GROUP,
            classType = DoYogaType.GROUP,
            lock = initObjects.first().lock,
        )
    }
    private val reqUpdateNotFound = DoYogaClass(
        id = updateIdNotFound,
        officeAddress = "Spot 18",
        trainer = "Shwarch",
        visibility = DoYogaVisibility.VISIBLE_TO_GROUP,
        classType = DoYogaType.GROUP,
        lock = initObjects.first().lock,
    )
    private val reqUpdateConc by lazy {
        DoYogaClass(
            id = updateConc.id,
            officeAddress = "Spot 18",
            trainer = "Shwarch",
            visibility = DoYogaVisibility.VISIBLE_TO_GROUP,
            classType = DoYogaType.GROUP,
            lock = lockBad,
        )
    }

    @Test
    fun updateSuccess() = runRepoTest {
        val result = repo.updateClass(DbClassRequest(reqUpdateSucc))
        assertEquals(true, result.isSuccess)
        assertEquals(reqUpdateSucc.id, result.data?.id)
        assertEquals(reqUpdateSucc.officeAddress, result.data?.officeAddress)
        assertEquals(reqUpdateSucc.trainer, result.data?.trainer)
        assertEquals(reqUpdateSucc.classType, result.data?.classType)
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun updateNotFound() = runRepoTest {
        val result = repo.updateClass(DbClassRequest(reqUpdateNotFound))
        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }
    @Test
    fun updateConcurrencyError() = runRepoTest {
        val result = repo.updateClass(DbClassRequest(reqUpdateConc))
        assertEquals(false, result.isSuccess)
        val error = result.errors.find { it.code == "concurrency" }
        assertEquals("lock", error?.field)
        assertEquals(updateConc.copy(time = result.data?.time), result.data)
    }
    companion object : BaseInitClasses("update") {
        override val initObjects: List<DoYogaClass> = listOf(
            createInitTestModel("update"),
            createInitTestModel("updateConc"),
        )
    }
}
