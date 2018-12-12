package com.orders

import grails.converters.JSON

import com.orders.embedded.OrderSchedule

class OrderController extends BaseController {
    def create() {
        String label = request.JSON.label
        String customer = request.JSON.customer
        String description = request.JSON.description
        Order order = new Order(label: label, customer: customer, description: description)
        if (!order.validate()) {
            handleValidationErrors(order)
            return
        }
        order.save(flush: true, failOnError: true)
        render text: "Order created: $label"
    }

    def delete() {
        String id = params.id
        Order order = Order.findById(id)

        if (!order) {
            render status: 404, text: "No order found for id: $id"
            return
        }

        order.delete(flush: true, failOnError: true)
        render text: "Order $id deleted"
    }

    def orders() {
        List<Order> orders = Order.findAll()
        render orders.collect { Order order ->
            [
                id: order.id,
                label: order.label,
                customer: order.customer,
                description: order.description
            ]
        } as JSON
    }

    def order() {
        String id = params.id
        Order order = Order.get(id)

        if (!order) {
            render status: 404, text: "No order found for id: $id"
            return
        }

        Map orderMap = [
            id: order.id,
            label: order.label,
            customer: order.customer,
            description: order.description
        ]

        render orderMap as JSON
    }

    def scheduleOrders() {
        String label = request.JSON.label
        String customer = request.JSON.customer
        String description = request.JSON.description
        Map<String, Set<String>> schedule = request.JSON.schedule

        RecurringOrder ro = new RecurringOrder([
            label: label,
            customer: customer
        ])

        if (description) {
            ro.description = description
        }

        if (schedule) {
            ro.orderSchedule = new OrderSchedule(schedule)
        }

        if (!ro.validate()) {
            handleValidationErrors(ro)
            return
        }

        // TODO: Figure out why the above call doesn't deep validate
        if (ro.orderSchedule && !ro.orderSchedule.validate()) {
            handleValidationErrors(ro.orderSchedule)
            return
        }

        ro.save(flush: true, failOnError: true)

        render text: "Order schedule created: $label"
    }
}