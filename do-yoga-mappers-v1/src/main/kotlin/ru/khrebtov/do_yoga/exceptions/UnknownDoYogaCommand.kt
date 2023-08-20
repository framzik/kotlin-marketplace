package ru.khrebtov.do_yoga.exceptions

import ru.khrebtov.do_yoga.common.models.DoYogaCommand

class UnknownDoYogaCommand(command: DoYogaCommand) : Throwable("Wrong command $command at mapping toTransport stage")
