package ru.khrebtov.rabbitapp.controller

import kotlinx.coroutines.*
import ru.khrebtov.rabbitapp.RabbitProcessorBase
import ru.khrebtov.rabbitapp.config.rabbitLogger

class RabbitController(
    private val processors: Set<RabbitProcessorBase>
) {

    fun start() = runBlocking {
        rabbitLogger.info("start init processors")
        processors.forEach {
            try {
                    launch { it.process() }
            } catch (e: RuntimeException) {
                // логируем, что-то делаем
                e.printStackTrace()
            }
        }
    }

}
