package com.orders.embedded

import grails.validation.Validateable

class OrderSchedule implements Validateable {
    static final String BREAKFAST = 'Breakfast'
    static final String LUNCH = 'Lunch'
    static final String DINNER = 'Dinner'

    private static final List<Set<String>> NORMAL_DAY_OPTIONS = [
        [BREAKFAST] as Set,
        [BREAKFAST, LUNCH] as Set,
        [BREAKFAST, DINNER] as Set,
        [LUNCH, DINNER] as Set,
        [BREAKFAST, LUNCH, DINNER] as Set,
    ]

    // TODO: Figure out why I can't create a local Closure or generator method for validator Closure
    static constraints = {
        sunday nullable: true, minSize: 1, maxSize: 1, inList: [[DINNER] as Set]
        monday nullable: true, minSize: 1, maxSize: 3, inList: NORMAL_DAY_OPTIONS
        tuesday nullable: true, minSize: 1, maxSize: 3, inList: NORMAL_DAY_OPTIONS
        wednesday nullable: true, minSize: 1, maxSize: 3, inList: NORMAL_DAY_OPTIONS
        thursday nullable: true, minSize: 1, maxSize: 3, inList: NORMAL_DAY_OPTIONS
        friday vnullable: true, minSize: 1, maxSize: 1, inList: [[BREAKFAST] as Set]
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