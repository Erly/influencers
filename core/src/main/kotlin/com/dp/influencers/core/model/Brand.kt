package com.dp.influencers.core.model

import com.dp.influencers.core.toSlug
import java.time.Instant

data class Brand(
        val id: Long? = null,
        var name: String,
        var friendlyName: String = name.toSlug(),
        var description: String? = null,
        var logo: String? = null,
        var link: String,
        var domain: String,
        var metaTitle: String? = null,
        var metaDescription: String? = null,
        var metaKeywords: String? = null,
        val creationDate: Long = Instant.now().toEpochMilli()
)