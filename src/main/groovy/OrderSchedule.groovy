package com.orders.embedded

import grails.validation.Validateable

class OrderSchedule implements Validateable {
    static final String BREAKFAST = 'Breakfast'
    static final String LUNCH = 'Lunch'
    static final String DINNER = 'Dinner'

    private static final List<String> FULL_DAY = [BREAKFAST, LUNCH, DINNER]

    // TODO: Figure out why I can't create a local Closure or generator method for validator Closure
    static constraints = {
        sunday validator: { val ->
            if (!val) {
                return true
            }
            if (!(val instanceof Set) || val.size() > 1) {
                return false
            }
            Set<String> invalidElements = val - [DINNER]
            if (invalidElements) {
                return false
            }
        }
        monday validator: { val ->
            if (!val) {
                return true
            }
            if (!(val instanceof Set) || val.size() > 3) {
                return false
            }
            Set<String> invalidElements = val - FULL_DAY
            if (invalidElements) {
                return false
            }
        }
        tuesday validator: { val ->
            if (!val) {
                return true
            }
            if (!(val instanceof Set) || val.size() > 3) {
                return false
            }
            Set<String> invalidElements = val - FULL_DAY
            if (invalidElements) {
                return false
            }
        }
        wednesday validator: { val ->
            if (!val) {
                return true
            }
            if (!(val instanceof Set) || val.size() > 3) {
                return false
            }
            Set<String> invalidElements = val - FULL_DAY
            if (invalidElements) {
                return false
            }
        }
        thursday validator: { val ->
            if (!val) {
                return true
            }
            if (!(val instanceof Set) || val.size() > 3) {
                return false
            }
            Set<String> invalidElements = val - FULL_DAY
            if (invalidElements) {
                return false
            }
        }
        friday validator: { val ->
            if (!val) {
                return true
            }
            if (!(val instanceof Set) || val.size() > 1) {
                return false
            }
            Set<String> invalidElements = val - [BREAKFAST]
            if (invalidElements) {
                return false
            }
        }
    }

    Set<String> sunday
    Set<String> monday
    Set<String> tuesday
    Set<String> wednesday
    Set<String> thursday
    Set<String> friday

    Map toMap() {
        // TODO: Don't add fields if they have null values
        return [
            sunday: sunday,
            monday: monday,
            tuesday: tuesday,
            wednesday: wednesday,
            thursday: thursday,
            friday: friday
        ]
    }

    @Override
    String toString() {
        return toMap().toMapString()
    }
}