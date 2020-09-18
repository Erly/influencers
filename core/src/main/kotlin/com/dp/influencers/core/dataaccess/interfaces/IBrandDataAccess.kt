package com.dp.influencers.core.dataaccess.interfaces

import com.dp.influencers.core.model.Brand
import java.sql.SQLException

interface IBrandDataAccess {

    @Throws(SQLException::class)
    fun getBrand() : List<Brand>

    @Throws(SQLException::class)
    fun setBrand(brand: Brand): Boolean
}