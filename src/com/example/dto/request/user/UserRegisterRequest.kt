package com.example.dto.request.user

import kotlinx.serialization.Serializable

@Serializable
data class UserRegisterRequest (
    val login: String,
    val password: String,
    val firstname: String,
    val lastname: String,
    val email: String,
    val otherContacts: Map<String, String>
)