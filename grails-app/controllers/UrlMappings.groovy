package com.orders

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?" { }

        "/orders" (controller: "order") {
            action = [GET: "orders", POST: "create"]
        }

        "/orders/schedule"(controller: "order") {
            action = [POST: "scheduleOrders"]
        }

        "/order/$id" (controller: "order") {
            action = [GET: "order", DELETE: "delete"]
        }

        "/meals" (controller: "meal") {
            action = [GET: "meals", POST: "create"]
        }

        "/meal/$id" (controller: "meal") {
            action = [GET: "meal", DELETE: "delete"]
        }

        "/user" (controller: "user") {
            action = [POST: "create"]
        }

        "/user/roles" (controller: "user") {
            action = [GET: "roles"]
        }

        "/user/$id/roles" (controller: "user") {
            action = [PUT: "updateRoles"]
        }

        "/user/login" (controller: "user") {
            action = [POST: "login"]
        }

        "/user/$id/logout" (controller: "user") {
            action = [POST: "logout"]
        }
    }
}