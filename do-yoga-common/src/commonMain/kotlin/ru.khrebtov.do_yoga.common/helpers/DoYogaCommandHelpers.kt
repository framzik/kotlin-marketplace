package ru.khrebtov.do_yoga.common.helpers

import ru.khrebtov.do_yoga.common.DoYogaContext
import ru.khrebtov.do_yoga.common.models.DoYogaCommand

fun DoYogaContext.isUpdatableCommand() =
    this.command in listOf(DoYogaCommand.CREATE, DoYogaCommand.UPDATE, DoYogaCommand.DELETE)
