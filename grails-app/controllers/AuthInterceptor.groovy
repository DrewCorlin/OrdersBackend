package com.orders

class AuthInterceptor {

    AuthInterceptor() {
        matchAll().excludes(controller: "user", action: "login")
    }

    boolean before() {
        String authToken = request.getHeader("authentication")
        String userId = request.getHeader("X-User-ID")
        User user = User.get(userId)
        if (!user) {
            log.error "message='Invalid user' | user=$userId"
            render status: 404 // Is this necessary?
            return false
        }
        if (user.authToken != authToken || (user.authTokenRefreshed + 1 < new Date())) {
            log.error "message='Invalid authentication' | authToken=$authToken | user=$userId"
            render status: 404 // Is this necessary?
            return false
        }
        user.authTokenRefreshed = new Date()
        user.save()
        return true
    }
}