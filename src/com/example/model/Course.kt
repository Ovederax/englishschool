package com.example.model

import com.example.dsl.Item
import com.example.dsl.ItemTable
import com.example.dsl.RepoDSL
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.UpdateBuilder

@Serializable
class Course (
        override var id: Int = -1,
        val name: String,
        val amount: Int,
) : Item

class Courses : ItemTable<Course>() {
    val name = varchar("name", 255)
    val amount = integer("amount")

    override fun fill(builder: UpdateBuilder<Int>, item: Course) {
        builder[name] = item.name
        builder[amount] = item.amount
    }

    override fun readResult(result: ResultRow) = Course (
        result[id].value,
        result[name],
        result[amount]
    )
}

val courses = Courses()

val coursesDSL = RepoDSL(courses)