package com.example.dto.response.course

import com.example.model.Lesson
import kotlinx.serialization.Serializable

@Serializable
class CourseGetResponse (
    var id: Int,
    val name: String,
    val amount: Int,
    var lessons: List<Lesson>
)