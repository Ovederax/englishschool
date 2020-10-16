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
class Lesson (
        val order: Int,
        val title: String,
        val content: String,
        override var id: Int = -1,
        val courseId: Int,
        val categoryId: Int,
) : Item

class Lessons : ItemTable<Lesson>() {
    val courseId = integer("courseId")
    val categoryId = integer("categoryId")
    val order = integer("order")
    val title = varchar("title", 40)
    val content = varchar("content", 40)

    override fun fill(builder: UpdateBuilder<Int>, item: Lesson) {
        builder[courseId] = item.courseId
        builder[categoryId] = item.categoryId
        builder[order] = item.order
        builder[title] = item.title
        builder[content] = item.content
    }

    override fun readResult(result: ResultRow) = Lesson (
            result[order],
            result[title],
            result[content],
            result[id].value,
            result[courseId],
            result[categoryId]
    )
}

val lessons = Lessons()

object lessonsDSL: RepoDSL<Lesson>(lessons) {
    fun findByCourse(id: Int): List<Lesson> =
        transaction {
            lessons.select { lessons.courseId eq id }.map { lessons.readResult(it) }
        }
}