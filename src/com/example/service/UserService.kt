package com.example.service

import com.example.controller.COOKIE_NAME
import com.example.dto.ClientDTO
import com.example.dto.TutorDTO
import com.example.dto.request.user.UserEditRequest
import com.example.dto.response.user.UserInfoResponse
import com.example.model.*
import io.ktor.application.*
import java.util.*

class UserService {
    fun get(id: Int): UserInfoResponse? {
        val user = usersDSL.read(id)
        user?.let {
            return UserInfoResponse(
                user.id,
                user.login,
                user.password,
                user.firstname,
                user.lastName,
                user.email,
            )
        }
        return null
    }

    fun getList(): List<UserInfoResponse> {
        return usersDSL.read().map {
            UserInfoResponse(it.id, it.login, it.password, it.firstname, it.lastName, it.email)
        }
    }

    fun updateUser(item: UserEditRequest): Boolean {
        val user = usersDSL.read(item.id)
        user?.let {
            item.password?.let { user.password = item.password }
            return usersDSL.update(user.id, user)
        }
        return false
    }

    fun registerClient(item: User): String? {
        val id = usersDSL.create(item)
        val clientId = clientsDSL.create(Client(id, 0 ))
        return login(item.login, item.password)
    }

    fun registerTutor(item: User): String? {
        val id = usersDSL.create(item)
        tutorsDSL.create(Tutor(id))
        return login(item.login, item.password)
    }

    fun login(login: String, password: String): String? {
        val user = usersDSL.findByLogin(login) ?: return null
        if(user.password != password) {
            return null
        }
        val token = UUID.randomUUID().toString()
        loginUserDSL.create(LoginUser(user.id, token))
        return token
    }

    fun logout(token: String): Boolean {
        val answer = loginUserDSL.deleteByToken(token)
        return answer
    }

    fun getClientByUser(user: User): ClientDTO? {
        val client = clientsDSL.findByUserId(user.id)?: return null
        return ClientDTO (
            user.id,
            client.id,
            user.login,
            user.password,
            user.firstname,
            user.lastName,
            user.email,
            client.money,
        )
    }

    fun getTutorByUser(user: User): TutorDTO? {
        val tutor = tutorsDSL.findByUserId(user.id)?: return null
        return TutorDTO (
            user.id,
            tutor.id,
            user.login,
            user.password,
            user.firstname,
            user.lastName,
            user.email,
        )
    }
    fun getUserByToken(call: ApplicationCall): User? {
        val token = call.request.cookies[COOKIE_NAME] ?: return null
        val loginUser = loginUserDSL.findByToken(token) ?: return null
        return usersDSL.read(loginUser.userId)
    }
    fun getClientByToken(call: ApplicationCall): ClientDTO? {
        val user = getUserByToken(call) ?: return null
        return getClientByUser(user)
    }

    fun getTutorByToken(call: ApplicationCall): TutorDTO? {
        val user = getUserByToken(call) ?: return null
        return getTutorByUser(user)
    }
}