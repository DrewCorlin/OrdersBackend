package com.orders

import org.springframework.security.authentication.encoding.LdapShaPasswordEncoder

class User {

    static constraints = {
        name unique: true
        roles minSize: 1
        passwordSecured nullable: true
    }

    String name
    List<String> roles = []
    transient String password
    String passwordSecured

    String id
    Date lastUpdated
    Date dateCreated

    void setPassword(String newPass) {
        LdapShaPasswordEncoder lspe = new LdapShaPasswordEncoder()
        passwordSecured = lspe.encodePassword(newPass, name as byte[])
    }

    @Override
    String toString() {
        return "[id: $id, name: $name, roles: $roles, passwordSecured: $passwordSecured]"
    }
}