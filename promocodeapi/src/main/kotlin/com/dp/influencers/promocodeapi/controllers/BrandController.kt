package com.dp.influencers.promocodeapi.controllers

import com.dp.influencers.core.model.Brand
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class BrandController {
    @GetMapping("/brands/{id}")
    fun getBrand(@PathVariable id: Long): Brand {
        return Brand(name = "test", domain = "test.com", link = "https://www.test.com")
    }
}