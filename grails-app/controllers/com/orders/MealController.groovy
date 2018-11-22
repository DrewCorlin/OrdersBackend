package com.orders

import grails.converters.JSON

class MealController extends BaseController {
    void create() {
        String label = request.JSON.label
        String description = request.JSON.description
        Meal meal = new Meal(label: label, description: description)
        if (!meal.validate()) {
            handleValidationErrors(meal)
            return
        }
        meal.save(flush: true, failOnError: true)
        render text: "Meal created: $label"
    }

    void delete() {
        String id = params.id
        Meal meal = Meal.findById(id)

        if (!meal) {
            render status: 404, text: "No meal found for id: $id"
            return
        }

        meal.delete(flush: true, failOnError: true)
        render text: "Meal $id deleted"
    }

    void getMeals() {
        List<Meal> meals = Meal.findAll()
        render meals.collect { Meal meal ->
            [
                id: meal.id,
                label: meal.label,
                description: meal.description
            ]
        } as JSON
    }

    void getMeal() {
        String id = params.id
        Meal meal = Meal.get(id)

        if (!meal) {
            render status: 404, text: "No meal found for id: $id"
            return
        }

        Map mealMap = [
            id: meal.id,
            label: meal.label,
            description: meal.description
        ]

        render mealMap as JSON
    }
}