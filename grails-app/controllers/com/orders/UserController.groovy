package com.orders

import grails.converters.JSON

class UserController implements BaseController {
    def create() {
        String name = request.JSON.name

        if (User.findByName(name)) {
            render status: 400, text: "Username taken"
            return
        }

        String password = request.JSON.password
        List<String> rolesRequested = request.JSON.roles

        List<Role> roles = Role.findAllByIdInList(rolesRequested)

        if (!roles) {
            render status: 400, text: "No roles found for given ids"
            return
        }

        List<String> roleIds = roles*.id
        User user = new User(name: name, password: password, roles: roleIds)

        if (!user.validate()) {
            handleValidationErrors(user)
            return
        }

        user.save(flush: true, failOnError: true)
        render text: "Created user: $name"
    }

    def delete() {
        String name = request.JSON.name
        String hashedPass = User.hashPassword(request.JSON.password, name)
        User user = User.findByNameAndPasswordSecured(name, hashedPass)

        if (!user) {
            log.error "message='No user found' | name=$name"
            render status: 404, text: "No user found for username and password"
            return
        }

        user.delete(flush: true, failOnError: true)
        render text: "Deleted user: $name"
    }

    def updateRoles() {
        String id = params.id
        User user = User.findById(id)

        if (!user) {
            log.error "message='No user found' | id=$id"
            render status: 404, text: "No user found for id: $id"
            return
        }

        List<Role> roles = Role.findAllByIdInList(request.JSON.roles)

        if (!roles) {
            render status: 400, text: "Roles must not be empty"
            return
        }

        if (roles*.id.size() != request.JSON.roles.size()) {
            render status: 400, text: "Invalid roles requested"
            return
        }

        user.roles = roles*.id

        if (!user.validate()) {
            handleValidationErrors(user)
            return
        }

        user.save(flush: true, failOnError: true)
        render text: "Updated roles for user $id"
    }

    def roles() {
        List<Role> roles = Role.findAll()
        render roles.collect { Role role ->
            [
                id: role.id,
                label: role.label
            ]
        } as JSON
    }
}