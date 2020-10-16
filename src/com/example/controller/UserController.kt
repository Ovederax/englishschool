package com.example.controller

import com.example.dto.request.user.UserLoginRequest
import com.example.dto.request.user.UserRegisterRequest
import com.example.model.User
import com.example.service.UserService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

const val COOKIE_NAME = "AUTH"

fun Route.userController(userService: UserService) {
    val logger = org.slf4j.LoggerFactory.getLogger("UserController")

    route("user") {
        post("client") {
            logger.debug("create client")
            parseBody(UserRegisterRequest.serializer())?.let {
                val user = User(
                        it.login,
                        it.password,
                        it.firstname,
                        it.lastname,
                        it.email,
                )
                try {
                    userService.registerClient(user)
                    val token = userService.login(user.login, user.password)
                    token?.let {
                        call.response.cookies.append(Cookie(COOKIE_NAME, token))
                    }
                    call.respond(HttpStatusCode.OK)
                } catch (ignored: Exception) {
                    call.respond(HttpStatusCode.BadRequest)
                }
            } ?: call.respond(HttpStatusCode.BadRequest)
        }

        post("tutor") {
            logger.debug("create tutor")
            parseBody(UserRegisterRequest.serializer())?.let {
                val user = User(
                        it.login,
                        it.password,
                        it.firstname,
                        it.lastname,
                        it.email,
                )
                try {
                    userService.registerTutor(user)
                    val token = userService.login(user.login, user.password)
                    token?.let {
                        call.response.cookies.append(Cookie(COOKIE_NAME, token))
                    }
                    call.respond(HttpStatusCode.OK)
                } catch (ignored: Exception) {
                    call.respond(HttpStatusCode.BadRequest)
                }
            } ?: call.respond(HttpStatusCode.BadRequest)
        }

        post("login") {
            logger.debug("login")
            parseBody(UserLoginRequest.serializer())?.let {
                try {
                    val token = userService.login(it.login, it.password)
                    token?.let {
                        call.response.cookies.append(Cookie(COOKIE_NAME, token))
                        call.respond(HttpStatusCode.OK)
                    }
                    token ?: call.respond(HttpStatusCode.BadRequest)
                } catch (ignored: Exception) {
                    call.respond(HttpStatusCode.BadRequest)
                }
            } ?: call.respond(HttpStatusCode.BadRequest)
        }

        post("logout") {
            logger.debug("logout")
            parseBody(UserLoginRequest.serializer())?.let {
                try {
                    val token = call.request.cookies[COOKIE_NAME]
                    if(token === null) {
                        call.respond(HttpStatusCode.BadRequest)
                    }
                    token?.let {
                        if(userService.logout(token)) {
                            call.respond(HttpStatusCode.OK)
                        } else {
                            call.respond(HttpStatusCode.BadRequest)
                        }
                    }
                } catch (ignored: Exception) {
                    call.respond(HttpStatusCode.BadRequest)
                }
            } ?: call.respond(HttpStatusCode.BadRequest)
        }
    }
}

