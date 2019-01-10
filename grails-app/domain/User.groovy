package com.orders

import org.springframework.security.authentication.encoding.LdapShaPasswordEncoder
import java.security.SecureRandom

class User {

    private static final String AVAILABLE_CHARACTERS = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890-_+=*/'
    private static final Integer AUTH_TOKEN_LENGTH = 30

    static constraints = {
        name unique: true
        roles minSize: 1
        passwordSecured nullable: true
        authToken nullable: true
        authTokenRefreshed nullable: true
    }

    static mapping = {
        name index: true
        compoundIndex name: 1, passwordSecured: 1
    }

    String name
    List<String> roles = []
    transient String password
    String passwordSecured
    String authToken
    Date authTokenRefreshed

    String id
    Date lastUpdated
    Date dateCreated
    // TODO: Create security service to use bcrypt,
    // make a static hashPass method on that we can access so we don't have to
    // define a bean on domain class
    static String hashPassword(String pass, String username) {
        LdapShaPasswordEncoder lspe = new LdapShaPasswordEncoder()
        return lspe.encodePassword(pass, username as byte[])
    }

    static String generateAuthToken() {
        SecureRandom random = new SecureRandom()
        String authToken = ""
        (0..AUTH_TOKEN_LENGTH).each {
            authToken += AVAILABLE_CHARACTERS[random.nextInt(AVAILABLE_CHARACTERS.size())]
        }
        return authToken
    }

    void setPassword(String newPass) {
        passwordSecured = hashPassword(newPass, name)
    }

    Map toMap() {
        // TODO: Don't add fields if they have null values
        return [
            id: id,
            name: name,
            roles: roles,
            authToken: authToken,
            authTokenRefreshed: authTokenRefreshed
        ]
    }

    @Override
    String toString() {
        return toMap().toMapString()
    }
}