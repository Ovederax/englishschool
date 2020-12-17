package com.example.service
import com.example.dto.response.course.CourseGetResponse
import com.example.dto.response.lesson.LessonGetResponse
import com.example.model.*

class CourseService {
    fun get(id: Int): CourseGetResponse? {
        val course = coursesDSL.read(id) ?: return null
        val lessons = lessonsDSL.findByCourse(id)
        val responseLessons = lessons.map {
            LessonGetResponse(it.title, it.order, it.content, categoriesDSL.read(it.categoryId))
        }
        return CourseGetResponse(
                course.id,
                course.name,
                course.amount,
                responseLessons,
        )
    }

    fun getList(): List<Course>
        = coursesDSL.read()

    fun update(item: Course) =
        coursesDSL.update(item.id, item)


    fun remove(id: Int): Boolean {
        return coursesDSL.delete(id)
    }

    fun create(item: Course): Int {
        return coursesDSL.create(item)
    }
}
