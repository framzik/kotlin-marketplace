package ru.otus.otuskotlin.marketplace.mappers.v1.exceptions

import ru.otus.otuskotlin.marketplace.common.models.DoYogaCommand

class UnknownDoYogaCommand(command: DoYogaCommand) : Throwable("Wrong command $command at mapping toTransport stage")
