package com.dp.influencers.core.model

import com.dp.influencers.core.model.enum.OfferTypeEnum

data class OfferType(
        val id: Long,
        val type: OfferTypeEnum
)