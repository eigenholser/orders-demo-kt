package com.zyx2.ordersdemokt

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import java.net.InetAddress
import java.util.*

class EventObserver(override var subject: Subject) : Observer() {
    private var producer: KafkaProducer<String, String>? = null
    private val topic = "order-submitted"

    init {
        this.subject = subject
        subject.attach(this)

        val config: Properties = Properties()
        config["client.id"] = InetAddress.getLocalHost().hostName
        config["bootstrap.servers"] = "localhost:9092"
        config["acks"] = "0"
        config["key.serializer"] = StringSerializer::class.java.canonicalName
        config["value.serializer"] = StringSerializer::class.java.canonicalName

        producer = KafkaProducer<String, String>(config)
    }

    public fun addMessage(message: String): Unit {
        subject.setState(message)
    }

    override fun update() {
        println("KAFKA EVENT SENT ${subject.timestamp()}")
        this.producer?.send(ProducerRecord(topic, "${subject.getState()}"))
        // Not caring about the result.
    }
}