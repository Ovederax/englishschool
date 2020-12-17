package com.example.controller

import com.example.dto.response.lesson.LessonGetResponse
import com.example.model.Lesson
import com.example.model.categoriesDSL
import com.example.service.LessonService
import com.example.service.UserService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.lessonController(service: LessonService, userService: UserService) {
    val path = "lesson"
    val serializer = Lesson.serializer()

    routing {
        route(path) {
            get {
                call.respond(service.getList())
            }

            post {
                if(userService.getTutorByToken(call) == null) {
                    call.respond(HttpStatusCode.Unauthorized)
                    return@post
                }
                call.respond(
                    parseBody(serializer)?.let { elem ->
                        if (service.create(elem) > 0)
                            HttpStatusCode.OK
                        else
                            HttpStatusCode.NotFound
                    } ?: HttpStatusCode.BadRequest
                )
            }
        }
        route("$path/{itemId}") {
            get {
                parseId()?.let { id ->
                    service.get(id)?.let { it ->
                        call.respond(LessonGetResponse(it.title, it.order, it.content, categoriesDSL.read(it.categoryId)))
                    } ?: call.respond(HttpStatusCode.NotFound)
                } ?: call.respond(HttpStatusCode.BadRequest)
            }
            put {
                if(userService.getTutorByToken(call) == null) {
                    call.respond(HttpStatusCode.Unauthorized)
                    return@put
                }
                var result = HttpStatusCode.BadRequest
                parseBody(serializer)?.let { elem ->
                    if (service.update(elem)) {
                        result = HttpStatusCode.OK
                    }
                }
                call.respond(result)
            }
            delete {
                if(userService.getTutorByToken(call) == null) {
                    call.respond(HttpStatusCode.Unauthorized)
                    return@delete
                }
                call.respond(
                    parseId()?.let { id ->
                        if (service.remove(id))
                            HttpStatusCode.OK
                        else
                            HttpStatusCode.NotFound
                    } ?: HttpStatusCode.BadRequest
                )
            }
        }
    }
}