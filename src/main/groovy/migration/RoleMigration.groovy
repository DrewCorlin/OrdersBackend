package com.orders.migration

import com.orders.Role
import com.orders.Constants

class RoleMigration extends Migration {

    RoleMigration() {
        id = "RoleMigration"
        successClosure = {
            List<Role> chefs = Role.findAllByLabel(Constants.CHEF_ROLE_LABEL)
            List<Role> customers = Role.findAllByLabel(Constants.CUSTOMER_ROLE_LABEL)
            List<Role> admins = Role.findAllByLabel(Constants.ADMIN_ROLE_LABEL)

            return (chefs.size() == 1 && customers.size() == 1 && admins.size() == 1 && chefs[0] && customers[0] && chefs[0])
        }
    }

    void run() {
        if (!Role.findByLabel(Constants.CHEF_ROLE_LABEL)) {
            new Role(label: Constants.CHEF_ROLE_LABEL).save(flush: true, failOnError: true, validate: true)
        }
        if (!Role.findByLabel(Constants.CUSTOMER_ROLE_LABEL)) {
            new Role(label: Constants.CUSTOMER_ROLE_LABEL).save(flush: true, failOnError: true, validate: true)
        }
        if (!Role.findByLabel(Constants.ADMIN_ROLE_LABEL)) {
            new Role(label: Constants.ADMIN_ROLE_LABEL).save(flush: true, failOnError: true, validate: true)
        }
    }
}