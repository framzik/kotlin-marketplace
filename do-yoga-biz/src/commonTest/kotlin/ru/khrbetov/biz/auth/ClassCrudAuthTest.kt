package ru.khrbetov.biz.auth

import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.khrebtov.biz.DoYogaClassProcessor
import ru.khrebtov.do_yoga.DoYogaClassStub
import ru.khrebtov.do_yoga.common.DoYogaContext
import ru.khrebtov.do_yoga.common.DoYogaCorSettings
import ru.khrebtov.do_yoga.common.models.DoYogaClass
import ru.khrebtov.do_yoga.common.models.DoYogaClassId
import ru.khrebtov.do_yoga.common.models.DoYogaClassPermissionClient
import ru.khrebtov.do_yoga.common.models.DoYogaCommand
import ru.khrebtov.do_yoga.common.models.DoYogaState
import ru.khrebtov.do_yoga.common.models.DoYogaUserId
import ru.khrebtov.do_yoga.common.models.DoYogaWorkMode
import ru.khrebtov.do_yoga.common.permissions.DoYogaPrincipalModel
import ru.khrebtov.do_yoga.common.permissions.DoYogaUserGroups
import ru.khrebtov.repo.inmemory.ClassRepoInMemory

@OptIn(ExperimentalCoroutinesApi::class)
class ClassCrudAuthTest {
    @Test
    fun createSuccessTest() = runTest {
        val userId = DoYogaUserId("123")
        val repo = ClassRepoInMemory()
        val processor = DoYogaClassProcessor(
            settings = DoYogaCorSettings(
                repoTest = repo
            )
        )
        val context = DoYogaContext(
            workMode = DoYogaWorkMode.TEST,
            classRequest = DoYogaClassStub.prepareResult {
                permissionsClient.clear()
                id = DoYogaClassId.NONE
            },
            command = DoYogaCommand.CREATE,
            principal = DoYogaPrincipalModel(
                id = userId,
                groups = setOf(
                    DoYogaUserGroups.USER,
                    DoYogaUserGroups.TEST,
                )
            )
        )

        processor.exec(context)
        assertEquals(DoYogaState.FINISHING, context.state)
        with(context.classResponse) {
            assertTrue { id.asString().isNotBlank() }
            assertContains(permissionsClient, DoYogaClassPermissionClient.READ)
        }
    }

    @Test
    fun readSuccessTest() = runTest {
        val adObj = DoYogaClassStub.get()
        val adId = adObj.id
        val repo = ClassRepoInMemory(initObjects = listOf(adObj))
        val processor = DoYogaClassProcessor(
            settings = DoYogaCorSettings(
                repoTest = repo
            )
        )
        val context = DoYogaContext(
            command = DoYogaCommand.READ,
            workMode = DoYogaWorkMode.TEST,
            classRequest = DoYogaClass(id = adId),
            principal = DoYogaPrincipalModel(
                groups = setOf(
                    DoYogaUserGroups.USER,
                    DoYogaUserGroups.TEST,
                )
            )
        )
        processor.exec(context)
        assertEquals(DoYogaState.FINISHING, context.state)
        with(context.classResponse) {
            assertTrue { id.asString().isNotBlank() }
            assertContains(permissionsClient, DoYogaClassPermissionClient.READ)
        }
    }

}
