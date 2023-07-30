package ru.khrebtov.rabbitapp

import com.rabbitmq.client.CancelCallback
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DeliverCallback
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.testcontainers.containers.RabbitMQContainer
import ru.khrebtov.api.v1.models.ClassCreateObject
import ru.khrebtov.api.v1.models.ClassCreateRequest
import ru.khrebtov.api.v1.models.ClassCreateResponse
import ru.khrebtov.api.v1.models.ClassDebug
import ru.khrebtov.api.v1.models.ClassRequestDebugMode
import ru.khrebtov.api.v1.models.ClassRequestDebugStubs
import ru.khrebtov.api.v1.models.ClassType
import ru.khrebtov.rabbitapp.config.RabbitConfig
import ru.khrebtov.rabbitapp.config.RabbitConfig.Companion.RABBIT_PASSWORD
import ru.khrebtov.rabbitapp.config.RabbitConfig.Companion.RABBIT_USER
import ru.khrebtov.rabbitapp.config.RabbitExchangeConfiguration
import ru.khrebtov.rabbitapp.controller.RabbitController
import ru.khrebtov.rabbitapp.processor.RabbitDirectProcessorV1
import ru.otus.otuskotlin.marketplace.api.v1.apiV1Mapper
import ru.otus.otuskotlin.marketplace.stubs.DoYogaClassStub

class RabbitMqTest {

    companion object {
        const val EXCHANGE_TYPE = "direct"
        const val TRANSPORT_EXCHANGE_V1 = "transport-exchange-v1"
    }

    val container by lazy {
        RabbitMQContainer("rabbitmq:latest").apply {
            withExposedPorts(5672, 15672)
            withUser(RABBIT_USER, RABBIT_PASSWORD)
            start()
        }
    }
    val config by lazy {
        RabbitConfig(
            port = container.getMappedPort(5672),
            host = container.host
        )
    }
    val processorConfigV1 = RabbitExchangeConfiguration(
        keyIn = "in-v1",
        keyOut = "out-v1",
        exchange = TRANSPORT_EXCHANGE_V1,
        queueIn = "v1-queue",
        queueOut = "v1-queue-out",
        consumerTag = "v1-consumer",
        exchangeType = EXCHANGE_TYPE
    )
    val processorV1 by lazy {
        RabbitDirectProcessorV1(
            config = config,
            processorConfig = processorConfigV1
        )
    }

    val controller by lazy {
        RabbitController(
            processors = setOf(processorV1)
        )
    }

    @BeforeTest
    @DelicateCoroutinesApi
    fun tearUp() {
        println("init controller")
        GlobalScope.launch {
            controller.start()
        }
        Thread.sleep(6000)
        // await when controller starts producers
        println("controller initiated")
    }

    @Test
    fun classCreateTestV1() {
        println("start test v1")
        val processorConfig = processorV1.processorConfig
        val keyIn = processorConfig.keyIn

        val connection1 = ConnectionFactory().apply {
            host = config.host
            port = config.port
            username = config.user
            password = config.password
        }.newConnection()
        connection1.createChannel().use { channel ->
            var responseJson = ""
            channel.exchangeDeclare(processorConfig.exchange, EXCHANGE_TYPE)
            val queueOut = channel.queueDeclare().queue
            channel.queueBind(queueOut, processorConfig.exchange, processorConfig.keyOut)
            val deliverCallback = DeliverCallback { consumerTag, delivery ->
                responseJson = String(delivery.body, Charsets.UTF_8)
                println(" [x] Received by $consumerTag: '$responseJson'")
            }
            channel.basicConsume(queueOut, true, deliverCallback, CancelCallback { })

            channel.basicPublish(processorConfig.exchange, keyIn, null, apiV1Mapper.writeValueAsBytes(classCreateV1))

            Thread.sleep(3000)
            // waiting for message processing
            println("RESPONSE: $responseJson")
            val response = apiV1Mapper.readValue(responseJson, ClassCreateResponse::class.java)
            val expected = DoYogaClassStub.get()

            assertEquals(expected.officeAddress, response.propertyClass?.officeAddress)
            assertEquals(expected.trainer, response.propertyClass?.trainer)
        }
    }

    private val classCreateV1 = with(DoYogaClassStub.get()) {
        ClassCreateRequest(
            propertyClass = ClassCreateObject(
                officeAddress = "someAddress",
                trainer = "Filatov and Ko",
                classType = ClassType.GROUP,
                time = "2028-07-21T17:32:28"
            ),
            requestType = "create",
            debug = ClassDebug(
                mode = ClassRequestDebugMode.STUB,
                stub = ClassRequestDebugStubs.SUCCESS
            )
        )
    }
}
