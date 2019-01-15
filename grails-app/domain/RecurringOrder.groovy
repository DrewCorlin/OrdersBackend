package com.orders

import com.orders.embedded.OrderSchedule

// TODO: Make unique with customer
class RecurringOrder {
    static embedded = ['orderSchedule']

    static constraints = {
        // TODO: Figure out why grails doesn't deep validate
        orderSchedule nullable: true, validator: { val ->
            if (!val) {
                return true
            }
            return val?.validate() ?: false
        }
        description nullable: true
    }

    String customer
    String description
    OrderSchedule orderSchedule

    String id
    Date lastUpdated
    Date dateCreated

    Map toMap() {
        // TODO: Don't add fields if they have null values
        return [
            id: id,
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