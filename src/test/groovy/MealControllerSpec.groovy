package com.orders

import grails.testing.web.controllers.ControllerUnitTest
import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class MealControllerSpec extends Specification implements ControllerUnitTest<MealController>, DomainUnitTest<Meal> {
    void "create() success"() {
        given:
            Map<String, String> requestBody = [
                label: "Chicken Fried Rice",
                description: "Low sodium soy sauce"
            ]
            request.json = requestBody
        when:
            controller.create()
        then:
            Meal meal = Meal.findByLabel(requestBody.label)
            meal.label == requestBody.label
            meal.description == requestBody.description
            response.text == "Meal created: Chicken Fried Rice"
    }

    void "create() validation failure"() {
        given:
            request.json = [:]
        when:
            controller.create()
        then:
            response.status == 400
            response.json.message == "Errors validating object class com.orders.Meal"
            response.json.errors.size() == 1
            response.json.errors.contains([reason: "nullable", field: "label", value: null])
    }

    void "delete() success"() {
        given:
            Meal meal = new Meal(label: "Shrimp Fried Rice", description: "Low sodium soy sauce")
            meal.save(flush: true, failOnError: true)
            params.id = "1"
        when:
            controller.delete()
            meal = Meal.findById("1")
        then:
            meal == null
            response.text == "Meal 1 deleted"
    }

    void "delete() failure on bad id"() {
        given:
            params.id = "1"
        when:
            controller.delete()
        then:
            response.status == 404
            response.text == "No meal found for id: 1"
    }

    void "meals() success"() {
        given:
            Meal m1 = new Meal([
                label: "Chicken",
                description: "Srirachi on the side"
            ])
            m1.save(flush: true, failOnError: true)
            Meal m2 = new Meal([
                label: "Sushi",
                description: "Tuna"
            ])
            m2.save(flush: true, failOnError: true)
            Meal m3 = new Meal([
                label: "Pasta",
                description: "Red sauce"
            ])
            m3.save(flush: true, failOnError: true)
        when:
            controller.meals()
        then:
            response.status == 200
            response.json.size() == 3
            response.json.contains([id: "1", label: "Chicken", description: "Srirachi on the side"])
            response.json.contains([id: "2", label: "Sushi", description: "Tuna"])
            response.json.contains([id: "3", label: "Pasta", description: "Red sauce"])
    }

    void "meal() success"() {
        given:
            Meal meal = new Meal([
                label: "Chicken",
                description: "Srirachi on the side"
            ])
            meal.save(flush: true, failOnError: true)
            params.id = "1"
        when:
            controller.meal()
        then:
            response.json == [id: "1", label: "Chicken", description: "Srirachi on the side"]
    }

    void "meal() failure"() {
        given:
            params.id = "1"
        when:
            controller.meal()
        then:
            response.status == 404
            response.text == "No meal found for id: 1"
    }
}