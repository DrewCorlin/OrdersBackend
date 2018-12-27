package com.orders.migration

import com.orders.Role

class RoleMigration extends Migration {

    private static final CHEF_LABEL = "CHEF"
    private static final CUSTOMER_LABEL = "CUSTOMER"

    RoleMigration() {
        id = "RoleMigration"
        successClosure = {
            List<Role> chefs = Role.findAllByLabel(CHEF_LABEL)
            List<Role> customers = Role.findAllByLabel(CUSTOMER_LABEL)

            return (chefs.size() == 1 && customers.size() == 1 && chefs[0] && customers[0])
        }
    }

    void run() {
        if (!Role.findByLabel(CHEF_LABEL)) {
            new Role(label: CHEF_LABEL).save(flush: true, failOnError: true, validate: true)
        }
        if (!Role.findByLabel(CUSTOMER_LABEL)) {
            new Role(label: CUSTOMER_LABEL).save(flush: true, failOnError: true, validate: true)
        }
    }
}