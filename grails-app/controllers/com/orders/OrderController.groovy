package com.orders

import grails.converters.JSON
import org.springframework.beans.factory.annotation.Value

import com.orders.embedded.OrderSchedule

class OrderController implements BaseController {

    @Value('${app.environment.timezone}')
    String timezone

    private static final Map<Integer, String> DAYS = [
        1: 'sunday',
        2: 'monday',
        3: 'tuesday',
        4: 'wednesday',
        5: 'thursday',
        6: 'friday',
        7: 'saturday'
    ]

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

        // TODO: Adjust for current timezone
        String day = DAYS[new Date()[Calendar.DAY_OF_WEEK]]

        String currentMeal = currentMeal()

        List<RecurringOrder> recurringOrders = []

        if (currentMeal) {
            recurringOrders = RecurringOrder.createCriteria().list {
                eq "orderSchedule.$day", currentMeal
            }
        }

        List<Map> response = orders.collect { Order order ->
            [
                id: order.id,
                label: order.label,
                customer: order.customer,
                description: order.description
            ]
        }

        recurringOrders.each { RecurringOrder ro ->
            response << [
                id: ro.id,
                label: ro.label,
                customer: ro.customer,
                description: ro.description
            ]
        }

        render response as JSON
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
        String customer = request.JSON.customer
        String description = request.JSON.description
        Map<String, Set<String>> schedule = request.JSON.schedule

        RecurringOrder ro = new RecurringOrder([
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

        ro.save(flush: true, failOnError: true)

        render text: "Order schedule created"
    }

    private String currentMeal() {
        Date date = new Date()
        TimeZone tz = TimeZone.getTimeZone(timezone)
        String time = date.format(Constants.TIME_OF_DAY_FORMAT, tz)
        List<String> times = time.split(':')
        Integer hour = times[0] as Integer

        // 7:00 - 10:59 is Breakfast
        // 11:00 - 14:00 is Lunch
        // 18:00 - 20:00 is Dinner

        if (hour > 7 && hour < 11) {
            return OrderSchedule.BREAKFAST
        } else if (hour >= 11 && hour < 14) {
            return OrderSchedule.LUNCH
        } else if (hour > 18 && hour < 20) {
            return OrderSchedule.DINNER
        }

        return null
    }
}