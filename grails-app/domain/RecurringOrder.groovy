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

    Map toMap() {
        // TODO: Don't add fields if they have null values
        return [
            id: id,
            label: label,
            customer: customer,
            description: description,
            orderSchedule: orderSchedule.toString()
        ]
    }

    @Override
    String toString() {
        return toMap().toMapString()
    }
}