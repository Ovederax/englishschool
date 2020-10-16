package com.example.service

import com.example.model.Category
import com.example.model.categoriesDSL

class CategoriesService : Service<Category> {
    override fun get(id: Int): Category? {
        return categoriesDSL.read(id)
    }

    fun getList(): List<Category> {
        return categoriesDSL.read()
    }

    override fun update(item: Category): Boolean {
        return categoriesDSL.update(item.id, item)
    }

    override fun remove(id: Int): Boolean {
        return categoriesDSL.delete(id)
    }

    override fun create(item: Category): Int {
        return categoriesDSL.create(item)
    }
}