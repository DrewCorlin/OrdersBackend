package com.orders

import grails.testing.web.controllers.ControllerUnitTest
import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class UserControllerSpec extends Specification implements ControllerUnitTest<UserController>, DomainUnitTest<User> {
    // void "createUser() success"() {
    //     given:
    //         Map<String, String> requestBody = [
    //             label: "Chicken Fried Rice",
    //             description: "Low sodium soy sauce"
    //         ]
    //         request.json = requestBody
    //     when:
    //         controller.createUser()
    //     then:
    //         Meal meal = Meal.findByLabel(requestBody.label)
    //         meal.label == requestBody.label
    //         meal.description == requestBody.description
    //         response.text == "Meal created: Chicken Fried Rice"
    // }

    // void "createUser() validation failure"() {
    //     given:
    //         request.json = [:]
    //     when:
    //         controller.createUser()
    //     then:
    //         response.status == 400
    //         response.json.message == "Errors validating object class com.orders.Meal"
    //         // why is this not a list of maps that also has an error for name?
    //         response.json.errors == [
    //             reason: "nullable",
    //             field: "description",
    //             value: null
    //         ]
    // }

    // void "deleteUser() success"() {
    //     given:
    //         Meal meal = new Meal(label: "Shrimp Fried Rice", description: "Low sodium soy sauce")
    //         meal.save(flush: true, failOnError: true)
    //         params.id = "1"
    //     when:
    //         controller.deleteUser()
    //         meal = Meal.findById("1")
    //     then:
    //         meal == null
    //         response.text == "Meal 1 deleted"
    // }

    // void "deleteUser() failure on bad id"() {
    //     given:
    //         params.id = "1"
    //     when:
    //         controller.deleteUser()
    //     then:
    //         response.status == 404
    //         response.text == "No meal found for id: 1"
    // }

    // void "getMeals() success"() {
    //     given:
    //         Meal m1 = new Meal([
    //             label: "Chicken",
    //             description: "Srirachi on the side"
    //         ])
    //         m1.save(flush: true, failOnError: true)
    //         Meal m2 = new Meal([
    //             label: "Sushi",
    //             description: "Tuna"
    //         ])
    //         m2.save(flush: true, failOnError: true)
    //         Meal m3 = new Meal([
    //             label: "Pasta",
    //             description: "Red sauce"
    //         ])
    //         m3.save(flush: true, failOnError: true)
    //     when:
    //         controller.getMeals()
    //     then:
    //         response.status == 200
    //         response.json.size() == 3
    //         response.json.contains([id: "1", label: "Chicken", description: "Srirachi on the side"])
    //         response.json.contains([id: "2", label: "Sushi", description: "Tuna"])
    //         response.json.contains([id: "3", label: "Pasta", description: "Red sauce"])
    // }

    // void "getMeal() success"() {
    //     given:
    //         Meal meal = new Meal([
    //             label: "Chicken",
    //             description: "Srirachi on the side"
    //         ])
    //         meal.save(flush: true, failOnError: true)
    //         params.id = "1"
    //     when:
    //         controller.getMeal()
    //     then:
    //         response.json == [id: "1", label: "Chicken", description: "Srirachi on the side"]
    // }

    // void "getMeal() failure"() {
    //     given:
    //         params.id = "1"
    //     when:
    //         controller.getMeal()
    //     then:
    //         response.status == 404
    //         response.text == "No meal found for id: 1"
    // }
}