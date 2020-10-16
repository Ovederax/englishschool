package com.example.controller

import com.example.model.Category
import com.example.model.Course
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import com.example.service.CategoriesService
import com.example.service.UserService
import io.ktor.http.*

fun Route.categoryController(categoriesService: CategoriesService, userService: UserService) {
    val path = "category"
    val serializer = Category.serializer()

    route(path) {
        get {
            call.respond(categoriesService.getList())
        }
        post {
            if(userService.getTutorByToken(call) == null) {
                call.respond(HttpStatusCode.Unauthorized)
                return@post
            }
            call.respond(
                    parseBody(serializer)?.let { elem -> {
                        val id = categoriesService.create(elem)
                        if(id != 0)
                            id
                        else
                            HttpStatusCode.NotFound
                    }
                    } ?: HttpStatusCode.BadRequest
            )
        }
    }
    route("${path}/{itemId}") {
        get {
            parseId()?.let { id ->
                categoriesService.get(id)?.let { elem ->
                    call.respond(elem)
                } ?: call.respond(HttpStatusCode.NotFound)
            } ?: call.respond(HttpStatusCode.BadRequest)
        }
        put {
            var result = HttpStatusCode.BadRequest
            parseBody(serializer)?.let { elem ->
                if (categoriesService.update(elem)) {
                    result = HttpStatusCode.OK
                }
            }
            call.respond(result)
        }
        delete {
            call.respond(
                    parseId()?.let { id ->
                        if (categoriesService.remove(id))
                            HttpStatusCode.OK
                        else
                            HttpStatusCode.NotFound
                    } ?: HttpStatusCode.BadRequest
            )
        }
    }
}

