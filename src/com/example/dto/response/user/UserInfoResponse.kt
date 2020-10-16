package com.example.dto.response.user

data class UserInfoResponse (
    val id: Int,
    val login: String,
    val password: String,
    val firstname: String,
    val lastName: String,
    val email: String,
)