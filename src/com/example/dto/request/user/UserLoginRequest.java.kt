package com.example.dto.request.user

import kotlinx.serialization.Serializable

@Serializable
data class UserLoginRequest (
    val login: String,
    val password: String,
)