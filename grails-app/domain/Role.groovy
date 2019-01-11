package com.orders

class Role {

    static mapping = {
        label index: true
    }

    String label

    String id
    Date lastUpdated
    Date dateCreated

    @Override
    String toString() {
        return "[id: $id, label: $label]"
    }
}