package com.orders.embedded

import grails.validation.Validateable

class OrderSchedule implements Validateable {
    static final String BREAKFAST = 'breakfast'
    static final String LUNCH = 'lunch'
    static final String DINNER = 'dinner'

    private static final List<Set<String>> NORMAL_DAY_OPTIONS = [
        [BREAKFAST] as Set,
        [BREAKFAST, LUNCH] as Set,
        [BREAKFAST, DINNER] as Set,
        [LUNCH, DINNER] as Set,
        [BREAKFAST, LUNCH, DINNER] as Set,
    ]

    static constraints = {
        sunday nullable: true, minSize: 1, maxSize: 1, inList: [[DINNER] as Set]
        monday nullable: true, minSize: 1, maxSize: 3, inList: NORMAL_DAY_OPTIONS
        tuesday nullable: true, minSize: 1, maxSize: 3, inList: NORMAL_DAY_OPTIONS
        wednesday nullable: true, minSize: 1, maxSize: 3, inList: NORMAL_DAY_OPTIONS
        thursday nullable: true, minSize: 1, maxSize: 3, inList: NORMAL_DAY_OPTIONS
        friday nullable: true, minSize: 1, maxSize: 1, inList: [[BREAKFAST] as Set]
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