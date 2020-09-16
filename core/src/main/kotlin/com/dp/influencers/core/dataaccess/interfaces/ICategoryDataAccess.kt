package com.dp.influencers.core.dataaccess.interfaces


import com.dp.influencers.core.model.Category
import java.sql.SQLException

interface ICategoryDataAccess {

    @Throws(SQLException::class)
    fun getCategories() : List<Category>

    @Throws(SQLException::class)
    fun setCategory(category: Category): Boolean
}