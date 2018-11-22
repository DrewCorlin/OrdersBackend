package com.orders

class User {
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

    void setPassword(String newPass) {
        passwordSecured = hashPassword(newPass)
    }

    String hashPassword(String newPass) {
        // replace this with a hash function
        return newPass
    }

    @Override
    String toString() {
        return "[id: $id, name: $name, roles: $roles, passwordSecured: $passwordSecured]"
    }
}