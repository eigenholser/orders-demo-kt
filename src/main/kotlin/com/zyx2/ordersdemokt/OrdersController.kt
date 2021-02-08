package com.zyx2.ordersdemokt

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class OrdersController {

    @Autowired
    lateinit var orderService: OrderService;

    @PostMapping("/orders")
    fun createOrder(@RequestBody order: ShopOrder): ResponseEntity<OrderCheckout> {
        return ResponseEntity<OrderCheckout>(orderService.checkout(order), HttpStatus.OK);
    }

}