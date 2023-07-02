package ru.khrebtov.springapp.service

import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import ru.khrebtov.biz.DoYogaClassProcessor
import ru.otus.otuskotlin.marketplace.common.DoYogaContext


@Service
class DoYogaClassBlockingProcessor {
    private val processor = DoYogaClassProcessor()

    fun exec(ctx: DoYogaContext) = runBlocking { processor.exec(ctx) }
}