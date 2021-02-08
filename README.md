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

## Tests

Run the tests:

    mvn test
