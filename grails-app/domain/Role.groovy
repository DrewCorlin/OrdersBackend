package com.orders

class Role {
    String label

    String id
    Date lastUpdated
    Date dateCreated

    @Override
    String toString() {
        return "[id: $id, label: $label]"
    }
}