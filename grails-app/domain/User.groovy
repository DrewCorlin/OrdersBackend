package com.orders

// import grails.plugin.springsecurity.SpringSecurityService
// import org.springframework.beans.factory.annotation.Autowired

class User {
    // @Autowired
    // SpringSecurityService springSecurityService

    static constraints = {
        name unique: true
        roles minSize: 1
    }

    String name
    List<String> roles = [] // Id's of role objects
    transient String password // Updated this to only store password hash
    String passwordSecured

    String id
    Date lastUpdated
    Date dateCreated

    // void setPassword(String newPass) {
    //     println newPass
    //     println "----"
    //     println springSecurityService
    //     println "++++++"
    //     passwordSecured = springSecurityService.encodePassword(name, newPass)
    //     println passwordSecured
    // }

    // boolean isCorrectPassword(String candidate) {
    //     println candidate
    //     println "========"
    //     println springSecurityService.encodePassword(name, candidate)
    //     println "========"
    //     return passwordSecured == springSecurityService.encodePassword(name, candidate)
    // }

    @Override
    String toString() {
        return "[id: $id, name: $name, roles: $roles, passwordSecured: $passwordSecured]"
    }
}