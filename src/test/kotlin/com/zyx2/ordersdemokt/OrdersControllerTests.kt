package com.zyx2.ordersdemokt

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class OrdersControllerTests() {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Test
    fun `Order POST returns correct response with HTTP_OK`() {
        val shopOrder = ShopOrder(arrayListOf("apple", "orange", "kumquat"))
        val response: ResponseEntity<OrderCheckout> = this.restTemplate
                .postForEntity("/orders", shopOrder)

        val body = response.body
        assertNotNull(body)
        assertEquals(response.statusCode, HttpStatus.OK)
        if (body != null) { // Make IDE happy
            assertTrue(body.items.contains("apple"))
            assertTrue(body.items.contains("orange"))
            assertFalse(body.items.contains("kumquat"))
        }
        if (body != null) { // Make IDE happy
            assertEquals("0.85".toBigDecimal(), body.total)
        }
    }

    @Test
    fun `Order POST and Simple Offer quantities returns correct response with HTTP_OK`() {
        val shopOrder = ShopOrder(arrayListOf("apple", "apple", "orange", "orange", "orange", "kumquat"))
        val response: ResponseEntity<OrderCheckout> = this.restTemplate
                .postForEntity("/orders", shopOrder)

        val body = response.body
        assertNotNull(body)
        assertEquals(HttpStatus.OK, response.statusCode)
        if (body != null) { // Make IDE happy
            assertTrue(body.items.contains("apple"))
            assertTrue(body.items.contains("orange"))
            assertFalse(body.items.contains("kumquat"))
        }
        if (body != null) { // Make IDE happy
            assertEquals("0.85".toBigDecimal(), body.total)
        }
    }
}