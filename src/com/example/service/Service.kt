package com.example.service

interface Service<T> {
    fun get(id: Int): T?
    fun create(item: T): Int
    fun update(item: T): Boolean
    fun remove(id: Int): Boolean
}