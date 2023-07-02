package ru.khrebtov.rabbitapp

import ru.khrebtov.biz.DoYogaClassProcessor
import ru.khrebtov.rabbitapp.config.RabbitConfig
import ru.khrebtov.rabbitapp.config.RabbitExchangeConfiguration
import ru.khrebtov.rabbitapp.controller.RabbitController
import ru.khrebtov.rabbitapp.processor.RabbitDirectProcessorV1



fun main() {
    val config = RabbitConfig()
    val adProcessor = DoYogaClassProcessor()

    val producerConfigV1 = RabbitExchangeConfiguration(
        keyIn = "in",
        keyOut = "out",
        exchange = "transport-exchange",
        queueIn = "queue",
        queueOut= "queue-out",
        consumerTag = "consumer",
        exchangeType = "direct"
    )


    val processor by lazy {
        RabbitDirectProcessorV1(
            config = config,
            processorConfig = producerConfigV1,
            processor = adProcessor
        )
    }

    val controller by lazy {
        RabbitController(
            processors = setOf(processor)
        )
    }
    controller.start()
}
