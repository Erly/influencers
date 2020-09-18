package com.dp.influencers.core.dataaccess.interfaces

import com.dp.influencers.core.model.OfferData
import java.sql.SQLException

interface IOfferDataAccess {
    @Throws(SQLException::class)
    fun getOffers(brandId: Long) : List<OfferData>

    @Throws(SQLException::class)
    fun setOffer(offer: OfferData): Boolean
}