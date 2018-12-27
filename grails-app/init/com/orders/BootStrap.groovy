package com.orders

import com.orders.migration.Migration
import com.orders.migration.MigrationTracker
import java.security.SecureClassLoader

class BootStrap {

    def init = { servletContext ->
        runMigrations()
    }

    def destroy = {
    }

    @SuppressWarnings("VariableTypeRequired")
    void runMigrations() {
        MigrationTracker mt = MigrationTracker.first() ?: new MigrationTracker()
        // ClassLoader is abstract so using secure child since loadClass is an instance method
        SecureClassLoader scl = new SecureClassLoader()
        List<String> migrationsToRun = Migration.migrations - mt.migrationsRun
        migrationsToRun.each { String m ->
            def migration
            try {
                Class migrationClass = scl.loadClass("com.orders.migration.$m")
                migration = migrationClass.newInstance()
                migration.run()
                migration.testSuccess()
                if (migration.successful) {
                    mt.migrationsRun << migration.id
                }
            } catch (Exception e) {
                log.error "Error running migration: ${migration?.id}", e
            }
        }
        mt.save(flush: true)
    }
}
