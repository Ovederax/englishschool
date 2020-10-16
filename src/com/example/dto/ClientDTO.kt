package com.example.dto

class ClientDTO (
    val clientId: Int,
    val userId: Int,
    val login: String,
    var password: String,
    val firstname: String,
    val lastName: String,
    val email: String,
    val money: Int = 0,
)