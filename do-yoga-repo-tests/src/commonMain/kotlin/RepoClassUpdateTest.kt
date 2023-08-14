package ru.otus.otuskotlin.marketplace.backend.repo.tests

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.otus.otuskotlin.marketplace.common.models.*
import ru.otus.otuskotlin.marketplace.common.repo.DbClassRequest
import ru.otus.otuskotlin.marketplace.common.repo.IClassRepository


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoClassUpdateTest {
    abstract val repo: IClassRepository
    protected open val updateSucc = initObjects[0]
    private val updateIdNotFound = DoYogaClassId("ad-repo-update-not-found")

    private val reqUpdateSucc by lazy {
        DoYogaClass(
            id = updateSucc.id,
            officeAddress = "Spot 18",
            trainer = "Shwarch",
            visibility = DoYogaVisibility.VISIBLE_TO_GROUP,
            classType = DoYogaType.GROUP,
        )
    }
    private val reqUpdateNotFound = DoYogaClass(
        id = updateIdNotFound,
        officeAddress = "Spot 18",
        trainer = "Shwarch",
        visibility = DoYogaVisibility.VISIBLE_TO_GROUP,
        classType = DoYogaType.GROUP,
    )

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

    companion object : BaseInitClasses("update") {
        override val initObjects: List<DoYogaClass> = listOf(
            createInitTestModel("update"),
            createInitTestModel("updateConc"),
        )
    }
}
