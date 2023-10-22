package ru.otus.otuskotlin.marketplace.app.common

import kotlinx.datetime.Clock
import ru.khrebtov.biz.DoYogaClassProcessor
import ru.khrebtov.do_yoga.common.DoYogaContext
import ru.khrebtov.do_yoga.common.helpers.asDoYogaError
import ru.khrebtov.do_yoga.common.helpers.fail
import ru.khrebtov.do_yoga.common.models.DoYogaCommand

suspend fun <T> DoYogaClassProcessor.process(
    fromTransport: suspend DoYogaContext.() -> Unit,
    sendResponse: suspend DoYogaContext.() -> T
): T {
    var command = DoYogaCommand.NONE
    return try {
        val ctx = DoYogaContext(
            timeStart = Clock.System.now(),
        )
        ctx.fromTransport()
        command = ctx.command
        exec(ctx)
        ctx.sendResponse()
    } catch (e: Throwable) {
        val ctx = DoYogaContext(
            timeStart = Clock.System.now(),
            command = command
        )

        ctx.fail(e.asDoYogaError())
        exec(ctx)
        sendResponse(ctx)
    }
}
