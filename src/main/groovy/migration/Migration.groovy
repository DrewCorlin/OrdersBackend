package com.orders.migration

abstract class Migration {

    static List<String> migrations = ["RoleMigration"]
    // id must be class name camelcase with capital first letter, unique
    String id
    Closure<Boolean> successClosure
    Boolean successful

    // run() method should be idempotent
    abstract void run()

    // TODO: Get logging working instead of println
    @SuppressWarnings("Println")
    void testSuccess() {
        successful = successClosure()
        if (successful) {
            println "Migration successful: $id"
        } else {
            println "Failed migration: $id - App still starting"
        }
    }
}