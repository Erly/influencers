package com.dp.influencers.core.model

import java.time.Instant

data class Promocode (
        val id: Long? = null,
        val offerGUID: Long? = null,
        val code: String? = null,
        val link: String? = null,
        val shown: Boolean,
        val disabled: Boolean,
        val creationDate: Long = Instant.now().toEpochMilli()
)