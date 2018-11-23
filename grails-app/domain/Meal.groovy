package com.orders

class Meal {
    static constraints = {
        description nullable: true
    }

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