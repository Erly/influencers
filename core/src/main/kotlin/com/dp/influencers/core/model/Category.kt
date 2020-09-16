package com.dp.influencers.core.model

import com.dp.influencers.core.toSlug
import java.time.Instant

data class Category(
        val id: Long? = null,
        val ParentId: Long? = null,
        var name: String,
        var friendlyName: String = name.toSlug(),
        var description: String? = null,
        var metaTitle: String? = null,
        var metaDescription: String? = null,
        var metaKeywords: String? = null,
        var image: String,
        val creationDate: Long = Instant.now().toEpochMilli()
)