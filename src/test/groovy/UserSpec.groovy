package com.orders

import spock.lang.Specification
import grails.testing.gorm.DataTest

class UserSpec extends Specification implements DataTest {

    void setupSpec() {
        mockDomains User, Role
    }

    void "test password replace by hash on persistance"() {
        given:
            User user = new User([
                name: "Drew C",
                password: "password123",
                roles: ["1"]
            ])
        when:
            user.save(flush: true, failOnError: true)
            User user2 = User.findById("1")
        then:
            user2.name == user.name
            user2.roles == user.roles
            !user2.password
            user2.passwordSecured != user.password
    }
}