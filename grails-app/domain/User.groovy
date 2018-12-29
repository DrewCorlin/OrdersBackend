package com.orders

import org.springframework.security.authentication.encoding.LdapShaPasswordEncoder

// TODO: Put indices on every field we search on
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

    static String hashPassword(String pass, String username) {
        LdapShaPasswordEncoder lspe = new LdapShaPasswordEncoder()
        return lspe.encodePassword(pass, username as byte[])
    }

    void setPassword(String newPass) {
        passwordSecured = hashPassword(newPass, name)
    }

    @Override
    String toString() {
        return "[id: $id, name: $name, roles: $roles, password: $password, passwordSecured: $passwordSecured]"
    }
}