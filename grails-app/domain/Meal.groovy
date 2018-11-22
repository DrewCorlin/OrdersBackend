package com.orders

class Meal {
    String label
    String description

    String id
    Date lastUpdated
    Date dateCreated

    @Override
    String toString() {
        return "[id: $id, label: $label, description: $description]"
    }
}