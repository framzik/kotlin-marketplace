package ru.khrebtov.biz

import ru.otus.otuskotlin.marketplace.common.DoYogaContext
import ru.otus.otuskotlin.marketplace.stubs.DoYogaClassStub

class DoYogaClassProcessor {
    suspend fun exec(ctx: DoYogaContext) {
        ctx.classResponse = DoYogaClassStub.get()
    }
}
