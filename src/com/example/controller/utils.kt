package com.example.controller

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.util.pipeline.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

fun PipelineContext<Unit, ApplicationCall>.parseId(id: String = "itemId") =
        call.parameters[id]?.toIntOrNull()

suspend fun <T> PipelineContext<Unit, ApplicationCall>
        .parseBody(serializer: KSerializer<T>) =
        try {
            Json.decodeFromString(
                    serializer,
                    call.receive()
            )
        } catch (e: Throwable) {
            null
        }