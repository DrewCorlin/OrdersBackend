package com.orders

import com.orders.embedded.OrderSchedule

class RecurringOrder extends Order {
    static embedded = ['orderSchedule']

    static constraints = {
        orderSchedule nullable: true
    }

    OrderSchedule orderSchedule

    @Override
    String toString() {
        return "[id: $id, label: $label, customer: $customer, description: $description, orderSchedule: ${orderSchedule.toString()}]"
    }
}