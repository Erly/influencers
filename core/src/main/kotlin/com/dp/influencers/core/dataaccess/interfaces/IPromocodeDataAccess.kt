package com.dp.influencers.core.dataaccess.interfaces

import com.dp.influencers.core.model.Promocode
import java.sql.SQLException


interface IPromocodeDataAccess {

    @Throws(SQLException::class)
    fun getPromocodes(guid: String) : List<Promocode>

    @Throws(SQLException::class)
    fun setPromocode(promocode: Promocode): Boolean
}