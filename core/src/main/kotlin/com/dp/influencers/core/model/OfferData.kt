package com.dp.influencers.core.model

import java.time.Instant

data class OfferData(
        val id: Long? = null,
        val brandId: Long,
        val categoryId: Long,
        val categoryName: String,
        val parentCategoryId: Long? = null,
        val parentCategoryName: String? = null,
        val offerType: OfferType,
        var name: String,
        var description: String? = null,
        var claim: String? = null,
        var terms: String? = null,
        var image: String? = null,
        var codeSnippet: String? = null,
        var disabled: Boolean,
        val startDate: Long = Instant.now().toEpochMilli(),
        val endDate: Long = Instant.now().toEpochMilli(),
        var metaTitle: String? = null,
        var metaDescription: String? = null,
        var metaKeywords: String? = null,
        val creationDate: Long = Instant.now().toEpochMilli(),
        val specialCategoryId: Long? = null,
        val specialCategoryName: String? = null

)