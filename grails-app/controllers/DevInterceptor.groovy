package com.orders

import org.springframework.beans.factory.annotation.Value

class DevInterceptor {

    @Value('${app.environment.adminClearanceLevel}')
    String ADMIN_LEVEL_CLEARANCE

    DevInterceptor() {
        matchAll()
    }

    boolean before() {
        String requestAdminLevel = request.JSON.adminLevel ?: params.adminLevel
        if (requestAdminLevel != ADMIN_LEVEL_CLEARANCE) {
            log.error "Invalid access attempted"
            return false
        }
        return true
    }
}