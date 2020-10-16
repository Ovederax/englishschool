package com.example.model

import com.example.dsl.Item
import com.example.dsl.ItemTable
import com.example.dsl.RepoDSL
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.transactions.transaction

@Serializable
class Client(
        val userId: Int,
        val money: Int = 0,
        val learnedLessons: List<Lesson> = ArrayList(),
        override var id: Int = -1
    ) : Item

class Clients : ItemTable<Client>() {
    val userId = integer("userId").references(users.id)
    val money = integer("money")

    override fun fill(builder: UpdateBuilder<Int>, item: Client) {
        builder[userId] = item.userId
        builder[money] = item.money
    }
    override fun readResult(result: ResultRow) = Client(
        result[userId],
        result[money],
        ArrayList(),
        result[id].value,
    )
}

val clients = Clients()

object clientsDSL : RepoDSL<Client>(clients) {
    fun findByUserId(userId: Int): Client? = transaction {
        val result = clients.select { clients.userId eq userId}.firstOrNull()
        if(result != null)
            clients.readResult(result)
        else
            null
    }
}