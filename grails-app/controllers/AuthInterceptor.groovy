package com.orders

class AuthInterceptor {

    AuthInterceptor() {
        matchAll().excludes(controller: "user", action: "login")
                  .excludes(controller: "user", action: "create")
    }

    boolean before() {
        String userId = request.getHeader("X-User-ID")
        User user = User.get(userId)
        if (!user) {
            log.error "message='Invalid user' | user=$userId"
            render status: 404 // Is this necessary?
            return false
        }
        String authToken = request.getHeader("X-Auth-Token")
        if (user.authToken != authToken || (user.authTokenRefreshed + 1 < new Date())) {
            log.error "message='Invalid authentication' | authToken=$authToken | user=$userId"
            render status: 404 // Is this necessary?
            return false
        }
        user.authTokenRefreshed = new Date()
        user.save()
        header "X-Auth-Token", authToken
        return true
    }
}