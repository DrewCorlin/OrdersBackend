package com.orders

class Role {
    // TODO: add an enum of the available roles with their labels
    String label

    String id
    Date lastUpdated
    Date dateCreated

    @Override
    String toString() {
        return "[id: $id, label: $label]"
    }
}