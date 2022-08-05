package com.mcontigo.api.controllers

import com.mcontigo.api.dto.price.BTCPriceDTO
import com.mcontigo.api.model.enums.FiatType
import com.mcontigo.api.services.BTCService
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CacheConfig(cacheNames = ["btc"])
class BTCController(
    private val btcService: BTCService
) {

    @GetMapping
    fun index(): List<BTCPriceDTO> {
        return btcService.fetchAll()
    }

    @GetMapping("/pair/{type}")
    @Cacheable("btc", key = "#type")
    fun fetchBtcPriceByFiat(@PathVariable type: FiatType): BTCPriceDTO {
        return btcService.fetchBtcPriceByFiat(type)
    }

    @PostMapping
    @CacheEvict("btc", allEntries = true, beforeInvocation = true)
    fun syncBtcPrice() {
        btcService.syncBtcPrice()
    }

}
