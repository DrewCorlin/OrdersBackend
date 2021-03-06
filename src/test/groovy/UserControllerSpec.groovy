package com.orders

import grails.testing.web.controllers.ControllerUnitTest
import grails.testing.gorm.DataTest
import spock.lang.Specification
import spock.lang.IgnoreRest

class UserControllerSpec extends Specification implements ControllerUnitTest<UserController>, DataTest {

    void setupSpec() {
        mockDomains User, Role
    }

    void setup() {
        new Role(label: Constants.CHEF_ROLE_LABEL).save(flush: true, failOnError: true)
    }

    void "create() success"() {
        given:
            request.json = [
                username: 'Drew C',
                password: 'password123'
            ]
            Role role = new Role([
                label: Constants.CUSTOMER_ROLE_LABEL
            ])
            role.save(flush: true, failOnError: true)
        when:
            controller.create()
        then:
            response.status == 200
            response.text == "Created user: Drew C"
        when:
            User user = User.findById("1")
        then:
            user.roles == ["2"] as Set<String>
    }

    void "create() failure for no customer role"() {
        given:
            request.json = [
                username: 'Drew C',
                password: 'password123',
                roles: ['1']
            ]
        when:
            controller.create()
        then:
            response.status == 500 // Exception will be caught and 500 rendered
    }

    void "create() validation failure"() {
        given:
            request.json = [roles: ['1']]
            Role role = new Role([
                label: Constants.CUSTOMER_ROLE_LABEL
            ])
            role.save(flush: true, failOnError: true)
        when:
            controller.create()
        then:
            response.status == 400
            response.json.message == "Errors validating object class com.orders.User"
            response.json.errors.size() == 2
            response.json.errors.contains([
                reason: "typeMismatch",
                field: "password",
                value: null
            ])
            response.json.errors.contains([
                reason: "nullable",
                field: "name",
                value: null
            ])
    }

    void "delete() success"() {
        given:
            User user = new User([
                name: 'Drew C',
                password: 'password123',
                roles: ['1']
            ])
            user.save(flush: true, failOnError: true)

            Role role =  new Role([
                label: Constants.CUSTOMER_ROLE_LABEL
            ])
            role.save(flush: true, failOnError: true)
            request.json = [
                username: "Drew C",
                password: "password123"
            ]
        when:
            controller.delete()
            user = User.findById("1")
        then:
            user == null
            response.text == "Deleted user: Drew C"
    }

    void "delete() failure on bad id"() {
        given:
            request.json = [
                username: "Drew C",
                password: "password123"
            ]
        when:
            controller.delete()
        then:
            response.status == 404
            response.text == "No user found for username and password"
    }

    void "test updateRoles()"() {
        given:
            params.id = "1"
            request.json = [
                roles: ['1', '2']
            ]
            User user = new User([
                name: "Drew C",
                password: "password123",
                roles: ["1"]
            ])
            user.save(flush: true, failOnError: true)
            Role customer = new Role([
                label: Constants.CUSTOMER_ROLE_LABEL
            ])
            customer.save(flush: true, failOnError: true)
        when:
            controller.updateRoles()
            user = User.findById("1")
        then:
            user.roles == ["1", "2"] as Set<String>
            response.status == 200
            response.text == "Updated roles for user 1"
    }

    void "test updateRoles() with invalid roles"() {
        given:
            params.id = "1"
            request.json = [
                roles: ['1', '2']
            ]
            User user = new User([
                name: "Drew C",
                password: "password123",
                roles: ["1"]
            ])
            user.save(flush: true, failOnError: true)
        when:
            controller.updateRoles()
            user = User.findById("1")
        then:
            response.status == 400
            response.text == "Invalid roles requested"
    }

    void "test roles()"() {
        given:
            new Role(label: Constants.CUSTOMER_ROLE_LABEL).save(flush: true, failOnError: true)
            new Role(label: Constants.ADMIN_ROLE_LABEL).save(flush: true, failOnError: true)
        when:
            controller.roles()
        then:
            response.status == 200
            response.json.size() == 3
            response.json.contains([label: Constants.CHEF_ROLE_LABEL, id: "1"])
            response.json.contains([label: Constants.CUSTOMER_ROLE_LABEL, id: "2"])
            response.json.contains([label: Constants.ADMIN_ROLE_LABEL, id: "3"])
    }

    void "test userRoles() success"() {
        given:
            new Role(label: Constants.CUSTOMER_ROLE_LABEL).save(flush: true, failOnError: true)
            new Role(label: Constants.ADMIN_ROLE_LABEL).save(flush: true, failOnError: true)
            new User([name: "Drew C", password: "password123", roles: ["1", "2"]]).save(flush: true, failOnError: true)
            params.id = "1"
        when:
            controller.userRoles()
        then:
            response.status == 200
            response.json.size() == 2
            response.json.contains([id: "1", label: Constants.CHEF_ROLE_LABEL])
            response.json.contains([id: "2", label: Constants.CUSTOMER_ROLE_LABEL])
    }
}