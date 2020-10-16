package com.example.model

import com.example.dsl.Item
import com.example.dsl.ItemTable
import com.example.dsl.RepoDSL
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.UpdateBuilder

@Serializable
class Category (
    val order: Int,
    val title: String,
    val description: String,
    override var id: Int = -1
) : Item

class Categories : ItemTable<Category>() {
    val order = integer("order")
    val title = varchar("title", 40)
    val description = varchar("description", 200)

    override fun fill(builder: UpdateBuilder<Int>, item: Category) {
        builder[order] = item.order
        builder[title] = item.title
        builder[description] = item.description
    }

    override fun readResult(result: ResultRow) = Category (
        result[order],
        result[title],
        result[description],
        result[id].value,
    )
}

val categories = Categories()

val categoriesDSL = RepoDSL(categories)