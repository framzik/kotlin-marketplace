package ru.khrebtov.biz.general

import ru.khrebtov.cor.ICorChainDsl
import ru.khrebtov.cor.worker
import ru.khrebtov.do_yoga.common.DoYogaContext
import ru.khrebtov.do_yoga.common.helpers.errorAdministration
import ru.khrebtov.do_yoga.common.helpers.fail
import ru.khrebtov.do_yoga.common.models.DoYogaWorkMode
import ru.khrebtov.do_yoga.common.repo.IClassRepository

fun ICorChainDsl<DoYogaContext>.initRepo(title: String) = worker {
    this.title = title
    description = """
        Вычисление основного рабочего репозитория в зависимости от запрошенного режима работы        
    """.trimIndent()
    handle {
        classRepo = when {
            workMode == DoYogaWorkMode.TEST -> settings.repoTest
            workMode == DoYogaWorkMode.STUB -> settings.repoStub
            else -> settings.repoProd
        }
        if (workMode != DoYogaWorkMode.STUB && classRepo == IClassRepository.NONE) fail(
            errorAdministration(
                field = "ru.khrebtov.do_yoga.common/repo",
                violationCode = "dbNotConfigured",
                description = "The database is unconfigured for chosen workmode ($workMode). " +
                        "Please, contact the administrator staff"
            )
        )
    }
}
