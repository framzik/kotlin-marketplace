package ru.otus.otuskotlin.marketplace.common.helpers

import ru.otus.otuskotlin.marketplace.common.DoYogaContext
import ru.otus.otuskotlin.marketplace.common.models.DoYogaCommand

fun DoYogaContext.isUpdatableCommand() =
    this.command in listOf(DoYogaCommand.CREATE, DoYogaCommand.UPDATE, DoYogaCommand.DELETE)
