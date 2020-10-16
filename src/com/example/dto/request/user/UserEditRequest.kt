package com.example.dto.request.user

import kotlinx.serialization.Serializable

@Serializable
data class UserEditRequest (
    val id: Int,
    val password: String?,
    val email: String?,
)