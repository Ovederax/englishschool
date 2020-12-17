package com.example.service

//import com.example.coursesDSL
import com.example.model.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun init() {
        Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver")

        transaction {
            addLogger(StdOutSqlLogger)
            create(users)
            create(clients)
            create(tutors)
            create(loginUsers)
            create(courses)
            create(lessons)
            create(categories)
            testData()
//            System.err.println(coursesDSL.read().toString())
        }
    }

    private fun testData() {
        val courseId1 = courses.insertAndGetId {
            it[name] = "Course one"
            it[amount] = 120
        }
        courses.insertAndGetId {
            it[name] = "Course two"
            it[amount] = 220
        }

        val categoryId1 = categories.insertAndGetId {
            it[order] = 1
            it[title] = "Beginner"
            it[description] = ""
        }

        lessons.insert {
            it[courseId] = courseId1.value
            it[categoryId] = categoryId1.value
            it[order] = 1
            it[title] = "Lesson One"
            it[content] = "Text"
        }
    }

    suspend fun <T> dbQuery(
            block: suspend () -> T): T =
            newSuspendedTransaction { block() }
}