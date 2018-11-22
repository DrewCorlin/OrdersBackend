package com.orders

import grails.converters.JSON

class OrderController extends BaseController {
    void create() {
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

    void delete() {
        String id = params.id
        Order order = Order.findById(id)

        if (!order) {
            render status: 404, text: "No order found for id: $id"
            return
        }

        order.delete(flush: true, failOnError: true)
        render text: "Order $id deleted"
    }

    void getOrders() {
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

    void getOrder() {
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
}