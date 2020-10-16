package com.example.errors;

class ServerError (
    val error: ErrorCode
) : Throwable()