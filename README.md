# Kotlin Spring Boot Demo

## Usage

Start the service:

    mvn spring-boot:run

Save JSON like this to file `order.json`:

    {
        "items": ["kumquat", "apple", "apple", "orange", "apple"]
    }

Make an API call:

    curl -v \
        -H "Content-type: application/json" \
        -d @order.json \
        http://localhost:8080/orders

## Kafka Setup

Setup Kafka according to [Kafka Quickstart](https://kafka.apache.org/quickstart):

    bin/kafka-topics.sh --create --topic order-submitted --bootstrap-server localhost:9092

Only `order-submitted` events are sent.

## Tests

Run the tests:

    mvn test
