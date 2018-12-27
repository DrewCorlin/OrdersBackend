package com.orders.migration

class MigrationTracker {
    Set<String> migrationsRun = []

    String id
    Date dateCreated
    Date lastUpdated

    @Override
    String toString() {
        return migrationsRun.toString()
    }
}