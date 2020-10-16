package com.example.service

import com.example.model.Lesson
import com.example.model.lessonsDSL


class LessonService: Service<Lesson> {
    fun getList(): List<Lesson>
            = lessonsDSL.read()

    override fun get(id: Int): Lesson? {
        return lessonsDSL.read(id)
    }

    override fun create(item: Lesson): Int {
       return lessonsDSL.create(item)
    }

    override fun update(item: Lesson): Boolean {
        return lessonsDSL.update(item.id, item)
    }

    override fun remove(id: Int): Boolean {
        return lessonsDSL.delete(id)
    }
}