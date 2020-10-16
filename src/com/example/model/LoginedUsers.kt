package com.example.model

import com.example.dsl.Item
import com.example.dsl.ItemTable
import com.example.dsl.RepoDSL
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.transactions.transaction

@Serializable
class LoginUser (
    val userId: Int,
    val token: String,
    override var id: Int = -1,
): Item

class LoginUsers : ItemTable<LoginUser>() {
    val userId = (integer("userId").references(users.id))
    val token = varchar("token", 255)

    override fun fill(builder: UpdateBuilder<Int>, item: LoginUser) {
        builder[userId] = item.userId
        builder[token] = item.token
    }

    override fun readResult(result: ResultRow) = LoginUser (
        result[userId],
        result[token],
        result[id].value,
    )
}

val loginUsers = LoginUsers()

object loginUserDSL : RepoDSL<LoginUser>(loginUsers) {
    fun deleteByToken(token: String): Boolean = transaction {
        loginUsers.deleteWhere { loginUsers.token eq token } > 0
    }
    fun findByToken(token: String): LoginUser? = transaction {
            val result = loginUsers.select { loginUsers.token eq token }.firstOrNull()
            if (result != null)
                loginUsers.readResult(result)
            else
                null
        }
}