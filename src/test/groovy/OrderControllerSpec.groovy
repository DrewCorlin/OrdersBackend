package com.orders

import grails.testing.web.controllers.ControllerUnitTest
import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class OrderControllerSpec extends Specification implements ControllerUnitTest<OrderController>, DomainUnitTest<Order> {
    void "create() success"() {
        given:
        Map<String, String> requestBody = [
            label: "Chicken Fried Rice",
            customer: "Drew C",
            description: "Low sodium soy sauce"
        ]
        request.json = requestBody

        when:
        controller.create()

        then:
        Order order = Order.findByCustomer(requestBody.customer)
        order.label == requestBody.label
        order.description == requestBody.description
        response.text == "Order created: Chicken Fried Rice"
    }

    void "create() validation failure"() {
        given:
        request.json = [:]

        when:
        controller.create()

        then:
        response.status == 400
        response.json.message == "Errors validating object class com.orders.Order"

        response.json.errors.size() == 2
        response.json.errors.contains([reason: "nullable", field: "label", value: null])
        response.json.errors.contains([reason: "nullable", field: "customer", value: null])
    }

    void "delete() success"() {
        given:
        Order order = new Order(label: "Shrimp Fried Rice", customer: "Drew")
        order.save(flush: true, failOnError: true)
        params.id = "1"

        when:
        controller.delete()
        order = Order.findById("1")

        then:
        order == null
        response.text == "Order 1 deleted"
    }

    void "delete() failure on bad id"() {
        given:
        params.id = "1"

        when:
        controller.delete()

        then:
        response.status == 404
        response.text == "No order found for id: 1"
    }

    void "orders() success"() {
        given:
        Order o1 = new Order([
            label: "Chicken",
            customer: "Drew",
            description: "Srirachi on the side"
        ])
        o1.save(flush: true, failOnError: true)
        Order o2 = new Order([
            label: "Sushi",
            customer: "John",
            description: "Tuna"
        ])
        o2.save(flush: true, failOnError: true)
        Order o3 = new Order([
            label: "Pasta",
            customer: "Samantha",
            description: "Red sauce"
        ])
        o3.save(flush: true, failOnError: true)

        when:
        controller.orders()

        then:
        response.status == 200
        response.json.size() == 3
        response.json.contains([id: "1", label: "Chicken", customer: "Drew", description: "Srirachi on the side"])
        response.json.contains([id: "2", label: "Sushi", customer: "John", description: "Tuna"])
        response.json.contains([id: "3", label: "Pasta", customer: "Samantha", description: "Red sauce"])
    }

    void "order() success"() {
        given:
        Order order = new Order([
            label: "Chicken",
            customer: "Drew",
            description: "Srirachi on the side"
        ])
        order.save(flush: true, failOnError: true)
        params.id = "1"

        when:
        controller.order()

        then:
        response.json == [id: "1", label: "Chicken", customer: "Drew", description: "Srirachi on the side"]
    }

    void "order() failure"() {
        given:
        params.id = "1"

        when:
        controller.order()

        then:
        response.status == 404
        response.text == "No order found for id: 1"
    }
}