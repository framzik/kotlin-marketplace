package ru.khrebtov.rabbitapp.processor

import com.rabbitmq.client.Channel
import com.rabbitmq.client.Delivery
import ru.khrebtov.api.v1.models.IRequest
import ru.khrebtov.biz.DoYogaClassProcessor
import ru.khrebtov.do_yoga.apiV1Mapper
import ru.khrebtov.rabbitapp.RabbitProcessorBase
import ru.khrebtov.rabbitapp.config.RabbitConfig
import ru.khrebtov.rabbitapp.config.RabbitExchangeConfiguration
import ru.khrebtov.do_yoga.common.helpers.addError
import ru.khrebtov.do_yoga.common.helpers.asDoYogaError
import ru.khrebtov.do_yoga.common.models.DoYogaState
import ru.khrebtov.do_yoga.common.DoYogaContext
import ru.khrebtov.do_yoga.fromTransport
import ru.khrebtov.do_yoga.toTransportClass

class RabbitDirectProcessorV1(
    config: RabbitConfig,
    processorConfig: RabbitExchangeConfiguration,
    private val processor: DoYogaClassProcessor = DoYogaClassProcessor(),
) : RabbitProcessorBase(config, processorConfig) {
    override suspend fun Channel.processMessage(message: Delivery, context: DoYogaContext) {
        apiV1Mapper.readValue(message.body, IRequest::class.java).run {
            context.fromTransport(this).also {
                println("TYPE: ${this::class.simpleName}")
            }
        }
        val response = processor.exec(context).run { context.toTransportClass() }
        apiV1Mapper.writeValueAsBytes(response).also {
            println("Publishing $response to ${processorConfig.exchange} exchange for keyOut ${processorConfig.keyOut}")
            basicPublish(processorConfig.exchange, processorConfig.keyOut, null, it)
        }.also {
            println("published")
        }
    }

    override fun Channel.onError(e: Throwable, context: DoYogaContext) {
        e.printStackTrace()
        context.state = DoYogaState.FAILING
        context.addError(error = arrayOf(e.asDoYogaError()))
        val response = context.toTransportClass()
        apiV1Mapper.writeValueAsBytes(response).also {
            basicPublish(processorConfig.exchange, processorConfig.keyOut, null, it)
        }
    }
}