package com.example.controller

import io.ktor.application.*
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.*

fun Application.index() {
    val indexPage = javaClass.getResource("/index.html").readText()
    routing {
        get("/") {
            call.respondText(indexPage, ContentType.Text.Html)
        }
    }
}
