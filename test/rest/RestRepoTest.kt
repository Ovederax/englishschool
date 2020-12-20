package rest

import com.example.controller.*
import com.example.dto.request.user.UserLoginRequest
import com.example.dto.request.user.UserRegisterRequest
import com.example.service.DatabaseFactory
import com.example.service.UserService
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.jackson.*
import io.ktor.routing.*
import io.ktor.server.testing.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class RestRepoTest {
    private val testPath = "/items"

    @Test
    fun testRest() {
        withTestApplication({
            install(CallLogging)
            install(ContentNegotiation) {
                jackson {
                    configure(SerializationFeature.INDENT_OUTPUT, true)
                }
            }
            DatabaseFactory.init()
            val userService = UserService()
            install(Routing) {
                userController(userService)
            }
        }) {
            val registerJson = Json.encodeToString(
                UserRegisterRequest.serializer(),
                UserRegisterRequest(
                "login",
                "password",
                "firstname",
                "lastname",
                "email",
                HashMap()
            ))

            var token: String?

            handleRequest(HttpMethod.Post, "/user/tutor") {
                setBodyAndHeaders(registerJson)
            }.apply {
                token = response.headers["Set-Cookie"]?.split(";")?.get(0)
                assertNotNull(token)
                assertStatus(HttpStatusCode.OK)
            }

            handleRequest(HttpMethod.Post, "/user/tutor") {
                setBodyAndHeaders("Wrong JSON")
            }.apply {
                assertStatus(HttpStatusCode.BadRequest)
            }

            handleRequest(HttpMethod.Post, "/user/logout"){
                setBodyAndHeaders("")
                token?.let { addHeader("Cookie", it) }
            }.apply {
                assertStatus(HttpStatusCode.OK)
            }

            val loginJson = Json.encodeToString(
                UserLoginRequest.serializer(),
                UserLoginRequest(
                    "login",
                    "password",
                ))

            handleRequest(HttpMethod.Post, "/user/login") {
                setBodyAndHeaders(loginJson)
            }.apply {
                assertNotNull(response.headers["Set-Cookie"])
                assertStatus(HttpStatusCode.OK)
            }
        }
    }
}

fun TestApplicationCall.assertStatus(status: HttpStatusCode) =
        assertEquals(status, response.status())

fun TestApplicationRequest.setBodyAndHeaders(body: String) {
    setBody(body)
    addHeader("Content-Type", "application/json")
    addHeader("Accept", "application/json")
}

fun <T> TestApplicationCall.parseResponse(
        serializer: KSerializer<T>
) =
        try {
            Json.decodeFromString(
                    serializer,
                    response.content ?: ""
            )
        } catch (e: Throwable) {
            null
        }