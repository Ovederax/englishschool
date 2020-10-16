package com.example.dsl
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

open class RepoDSL<T : Item>(
        protected val table: ItemTable<T>
) : Repo<T> {

    override fun create(element: T) =
            transaction {
                table.insertAndGetIdItem(element).value
            }

    override fun read(id: Int) =
            transaction {
                table
                        .select { table.id eq id }
                        .firstOrNull()
                        ?.let {
                            table.readResult(it)
                        }
            }

    override fun update(id: Int, element: T) =
            transaction {
                table.updateItem(id, element) > 0
            }

    override fun delete(id: Int) =
            transaction {
                table.deleteWhere { table.id eq id } > 0
            }

    override fun read() =
            transaction {
                table.selectAll()
                        .mapNotNull {
                            table.readResult(it)
                        }
            }
}