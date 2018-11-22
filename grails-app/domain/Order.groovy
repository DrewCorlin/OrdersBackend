package com.orders

// TODO: make this unique for label & customer
// Also update logic to validate and use BaseControler.handleValidationErrors
class Order {
    static constraints = {
        description nullable: true
    }
    String label
    String customer
    String description

    String id
    Date lastUpdated
    Date dateCreated
}