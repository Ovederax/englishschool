package com.example.model

import com.example.dsl.Item
import com.example.dsl.ItemTable
import com.example.dsl.RepoDSL
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.transactions.transaction

@Serializable
open class User (
        open val login: String,
        open var password: String,
        open val firstname: String,
        open val lastName: String,
        open val email: String,
        override var id: Int = -1,
) : Item

class Users: ItemTable<User>() {
    val login = varchar("login", 40)
    val password = varchar("password", 40)
    val firstname = varchar("firstname", 40)
    val lastName = varchar("lastName", 40)
    val email = varchar("email", 40)
    // var otherContacts: Map<String, String>

    override fun fill(builder: UpdateBuilder<Int>, item: User) {
        builder[login] = item.login
        builder[password] = item.password
        builder[firstname] = item.firstname
        builder[lastName] = item.lastName
        builder[email] = item.email
    }

    override fun readResult(result: ResultRow) = User (
        result[login],
        result[password],
        result[firstname],
        result[lastName],
        result[email],
        result[id].value,
    )
}

val users = Users()

object usersDSL : RepoDSL<User>(users) {
    fun findByLogin (login: String) =
        transaction {
            users
                .select { users.login eq login }
                .firstOrNull()
                ?.let { table.readResult(it) }
        }
}