package com.orders

import com.orders.embedded.OrderSchedule

class RecurringOrder extends Order {
    static embedded = ['orderSchedule']

    static constraints = {
        // TODO: Figure out why grails doesn't deep validate
        orderSchedule nullable: true, validator: { val ->
            if (!val) {
                return true
            }
            return val?.validate() ?: false
        }
    }

    OrderSchedule orderSchedule

    @Override
    String toString() {
        return "[id: $id, label: $label, customer: $customer, description: $description, orderSchedule: ${orderSchedule.toString()}]"
    }
}