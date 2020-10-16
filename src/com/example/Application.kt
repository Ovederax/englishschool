package com.example

import com.example.controller.*
import com.example.service.*
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.jackson.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main(/*args: Array<String>*/): Unit  {
    embeddedServer(Netty, port = 8000, module = Application::module).start(wait = true)
}

fun Application.module() {
    install(CallLogging)
    install(ContentNegotiation) {
        jackson {
            configure(SerializationFeature.INDENT_OUTPUT, true)
        }
    }

    DatabaseFactory.init()
    val courseService = CourseService()
    val lessonService = LessonService()
    val userService = UserService()
    val categoriesService = CategoriesService()

    install(Routing) {
        index()
        userController(userService)
        courseController(courseService, userService)
        categoryController(categoriesService, userService)
        lessonController(lessonService, userService)
    }
}

