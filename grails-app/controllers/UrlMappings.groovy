package com.orders

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?" {
            constraints {
                // apply constraints here
            }
        }

        "/orders" (controller: "order") {
            action = [GET: "getOrders", POST: "create"]
        }

        "/order/$id" (controller: "order") {
            action = [GET: "getOrder", DELETE: "delete"]
        }

        "/meals" (controller: "meal") {
            action = [GET: "getMeals", POST: "create"]
        }

        "/meal/$id" (controller: "meal") {
            action = [GET: "getMeal", DELETE: "delete"]
        }

        "/user" (controller: "user") {
            action = [POST: "create"]
        }

        "/user/roles" (controller: "user") {
            action = [GET: "getRoles"]
        }

        "/user/$id/roles" (controller: "user") {
            action = [PUT: "updateRoles"]
        }
    }
}