package com.orders

import org.springframework.beans.factory.annotation.Value

class DevInterceptor {

    @Value('${app.environment.adminClearanceLevel}')
    String ADMIN_LEVEL_CLEARANCE

    DevInterceptor() {
        matchAll()
    }

    boolean before() {
        println ADMIN_LEVEL_CLEARANCE
        if (request.JSON.adminLevel != ADMIN_LEVEL_CLEARANCE) {
            log.error "Invalid access attempted"
            return false
        }
        return true
    }
}