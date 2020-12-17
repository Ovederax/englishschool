package com.example.dto.response.lesson;

import com.example.model.Category
import kotlinx.serialization.Serializable

@Serializable
class LessonGetResponse (
    val title: String,
    val order: Int,
    val content: String,
    val category: Category?,
)
