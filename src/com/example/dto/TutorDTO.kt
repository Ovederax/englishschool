package com.example.dto

data class TutorDTO (
    val tutorId: Int,
    val userId: Int,
    val login: String,
    var password: String,
    val firstname: String,
    val lastName: String,
    val email: String,
)