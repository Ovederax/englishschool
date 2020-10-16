package com.example.model

import com.example.dsl.Item
import com.example.dsl.ItemTable
import com.example.dsl.RepoDSL
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.transactions.transaction


class Tutor(
    val userId: Int,
    override var id: Int = -1
) : Item

class Tutors: ItemTable<Tutor>() {
    val userId = (integer("userId").references(users.id))

    override fun fill(builder: UpdateBuilder<Int>, item: Tutor) {
        builder[userId] = item.userId
    }

    override fun readResult(result: ResultRow) = Tutor (
        result[userId],
        result[id].value,
    )
}

val tutors = Tutors()

object tutorsDSL : RepoDSL<Tutor>(tutors) {
    fun findByUserId(userId: Int): Tutor? = transaction {
        val result = tutors.select { tutors.userId eq userId}.firstOrNull()
        if(result != null)
            tutors.readResult(result)
        else
            null
    }
}