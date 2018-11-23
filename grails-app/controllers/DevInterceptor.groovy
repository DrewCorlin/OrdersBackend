package com.orders

import org.springframework.beans.factory.annotation.Value

class DevInterceptor {

    @Value('$app.environment.adminClearanceLevel')
    private static final String ADMIN_LEVEL_CLEARANCE

    public DevInterceptor() {
        matchAll()
    }
    boolean before() {
        if (request.JSON.adminLevel == ADMIN_LEVEL_CLEARANCE) {
            log.error "Invalid access attempted"
            return false
        }
        return true
    }
}