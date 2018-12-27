package com.orders

import grails.converters.JSON

abstract class BaseController {
    def handleException(Exception e) {
        log.error "message='Unexpected server error'", e
        render status: 500, text: "Unexpected server error"
    }

    // Expects to be passed a validateable object
    def handleValidationErrors(Object obj) {
        Map response = [
            message: "Errors validating object ${obj.getClass()}",
            errors: []
        ]
        // Extra safety to still let user know that validation failed in some way
        try {
            obj.errors.allErrors.each {
                response.errors << [
                    field: it.field,
                    value: it.rejectedValue,
                    reason: it.code // Not sure if this is really what we want
                ]
            }
        } catch (Exception e) {
            log.error "message='Error during handleValidationErrors'", e
            render status: 400, text: "Error validating ${obj?.getClass()}"
            return
        }
        render status: 400, text: response as JSON
    }
}