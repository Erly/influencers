package com.dp.influencers.core.model

import com.dp.influencers.core.model.enum.CategoryTypeEnum
import com.dp.influencers.core.toSlug
import java.time.Instant

data class Category(
        val id: Long? = null,
        val parentId: Long? = null,
        var name: String,
        var categoryType : CategoryTypeEnum,
        var friendlyName: String = name.toSlug(),
        var description: String? = null,
        var metaTitle: String? = null,
        var metaDescription: String? = null,
        var metaKeywords: String? = null,
        var image: String,
        var isMenu: Boolean,
        val creationDate: Long = Instant.now().toEpochMilli()
)