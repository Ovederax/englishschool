package com.example.controller

import com.example.model.Course
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import com.example.service.CourseService
import com.example.service.UserService

fun Route.courseController(courseService: CourseService, userService: UserService) {
    val path = "course"
    val serializer = Course.serializer()

    route(path) {
        get {
            call.respond(courseService.getList())
        }
        post {
            val tutor = userService.getTutorByToken(call)
            if(tutor == null) {
                call.respond(HttpStatusCode.Unauthorized)
                return@post
            }
            call.respond(
                parseBody(serializer)?.let { elem -> {
                    val id = courseService.create(elem)
                    if(id != 0)
                        id.toString()
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
                courseService.get(id)?.let { elem ->
                    call.respond(elem)
                } ?: call.respond(HttpStatusCode.NotFound)
            } ?: call.respond(HttpStatusCode.BadRequest)
        }
        put {
            var result = HttpStatusCode.BadRequest
            parseBody(serializer)?.let { elem ->
                if (courseService.update(elem)) {
                    result = HttpStatusCode.OK
                }
            }
            call.respond(result)
        }
        delete {
            call.respond(
                parseId()?.let { id ->
                    if (courseService.remove(id))
                        HttpStatusCode.OK
                    else
                        HttpStatusCode.NotFound
                } ?: HttpStatusCode.BadRequest
            )
        }
    }
}

