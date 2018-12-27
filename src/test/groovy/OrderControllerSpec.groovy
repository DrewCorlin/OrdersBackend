package com.orders

import grails.testing.web.controllers.ControllerUnitTest
import grails.testing.gorm.DataTest
import spock.lang.Specification
import spock.lang.IgnoreRest

import com.orders.embedded.OrderSchedule

class OrderControllerSpec extends Specification implements ControllerUnitTest<OrderController>, DataTest {

    void setupSpec() {
        mockDomains Order, RecurringOrder
    }

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

    void "scheduleOrders() success"() {
        given:
            request.json = [
                label: "recurring order",
                customer: "Drew",
                description: "every meal",
                schedule: [
                    sunday: [OrderSchedule.DINNER] as Set,
                    monday: [OrderSchedule.BREAKFAST, OrderSchedule.LUNCH, OrderSchedule.DINNER] as Set,
                    tuesday: [OrderSchedule.BREAKFAST, OrderSchedule.LUNCH, OrderSchedule.DINNER] as Set,
                    wednesday: [OrderSchedule.BREAKFAST, OrderSchedule.LUNCH, OrderSchedule.DINNER] as Set,
                    thursday: [OrderSchedule.BREAKFAST, OrderSchedule.LUNCH, OrderSchedule.DINNER] as Set,
                    friday: [OrderSchedule.BREAKFAST] as Set
                ]
            ]
        when:
            controller.scheduleOrders()
        then:
            RecurringOrder ro = RecurringOrder.findById("1")

            ro.label == "recurring order"
            ro.customer == "Drew"
            ro.description == "every meal"
            ro.orderSchedule.sunday == ["Dinner"] as Set
            ro.orderSchedule.monday == ["Breakfast", "Lunch", "Dinner"] as Set
            ro.orderSchedule.tuesday == ["Breakfast", "Lunch", "Dinner"] as Set
            ro.orderSchedule.wednesday == ["Breakfast", "Lunch", "Dinner"] as Set
            ro.orderSchedule.thursday == ["Breakfast", "Lunch", "Dinner"] as Set
            ro.orderSchedule.friday == ["Breakfast"] as Set
    }

    void "test scheduleOrders() success without description"() {
        given:
            request.json = [
                label: "recurring order",
                customer: "Drew",
                schedule: [
                    sunday: [OrderSchedule.DINNER] as Set,
                    monday: [OrderSchedule.BREAKFAST, OrderSchedule.LUNCH, OrderSchedule.DINNER] as Set,
                    tuesday: [OrderSchedule.BREAKFAST, OrderSchedule.LUNCH, OrderSchedule.DINNER] as Set,
                    wednesday: [OrderSchedule.BREAKFAST, OrderSchedule.LUNCH, OrderSchedule.DINNER] as Set,
                    thursday: [OrderSchedule.BREAKFAST, OrderSchedule.LUNCH, OrderSchedule.DINNER] as Set,
                    friday: [OrderSchedule.BREAKFAST] as Set
                ]
            ]
        when:
            controller.scheduleOrders()
        then:
            RecurringOrder ro = RecurringOrder.findById("1")

            ro.label == "recurring order"
            ro.customer == "Drew"
            ro.orderSchedule.sunday == ["Dinner"] as Set
            ro.orderSchedule.monday == ["Breakfast", "Lunch", "Dinner"] as Set
            ro.orderSchedule.tuesday == ["Breakfast", "Lunch", "Dinner"] as Set
            ro.orderSchedule.wednesday == ["Breakfast", "Lunch", "Dinner"] as Set
            ro.orderSchedule.thursday == ["Breakfast", "Lunch", "Dinner"] as Set
            ro.orderSchedule.friday == ["Breakfast"] as Set
            !ro.description
    }

    void "test scheduleOrders() success with some empty days"() {
        given:
            request.json = [
                label: "recurring order",
                customer: "Drew",
                schedule: [
                    sunday: [OrderSchedule.DINNER] as Set,
                    monday: [OrderSchedule.BREAKFAST, OrderSchedule.LUNCH, OrderSchedule.DINNER] as Set,
                    tuesday: [OrderSchedule.BREAKFAST, OrderSchedule.LUNCH, OrderSchedule.DINNER] as Set,
                    wednesday: [OrderSchedule.BREAKFAST, OrderSchedule.LUNCH, OrderSchedule.DINNER] as Set,
                    thursday: [OrderSchedule.BREAKFAST, OrderSchedule.DINNER] as Set
                ]
            ]
        when:
            controller.scheduleOrders()
        then:
            RecurringOrder ro = RecurringOrder.findById("1")

            ro.label == "recurring order"
            ro.customer == "Drew"
            ro.orderSchedule.sunday == ["Dinner"] as Set
            ro.orderSchedule.monday == ["Breakfast", "Lunch", "Dinner"] as Set
            ro.orderSchedule.tuesday == ["Breakfast", "Lunch", "Dinner"] as Set
            ro.orderSchedule.wednesday == ["Breakfast", "Lunch", "Dinner"] as Set
            ro.orderSchedule.thursday == ["Breakfast", "Dinner"] as Set
            !ro.orderSchedule.friday
            !ro.description
    }

    void "test scheduleOrders() success with no schedule"() {
        given:
            request.json = [
                label: "recurring order",
                customer: "Drew"
            ]
        when:
            controller.scheduleOrders()
        then:
            RecurringOrder ro = RecurringOrder.findById("1")

            ro.label == "recurring order"
            ro.customer == "Drew"
            !ro.description
            !ro.orderSchedule
    }

    void "test scheduleOrders() failure for bad schedule format"() {
        given:
            request.json = [
                label: "recurring order",
                customer: "Drew",
                schedule: [
                    sunday: ["not dinner"]
                ]
            ]
        when:
            controller.scheduleOrders()
        then:
            RecurringOrder ro = RecurringOrder.findById("1")
            !ro
            response.status == 400
            response.json.errors.size() == 1
            response.json.errors[0].field == "orderSchedule"
    }
}