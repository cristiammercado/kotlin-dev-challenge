package com.mcontigo.api.controllers

import com.mcontigo.api.dto.price.BTCPriceDTO
import com.mcontigo.api.model.enums.FiatType
import com.mcontigo.api.services.BTCService
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.http.ResponseEntity
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
    fun fetchAll(): ResponseEntity<List<BTCPriceDTO>> {
        return ResponseEntity.ok().body(btcService.fetchAll())
    }

    @GetMapping("/pair/{type}")
    @Cacheable("btc", key = "#type")
    fun fetchBtcPriceByFiat(@PathVariable type: FiatType): ResponseEntity<BTCPriceDTO> {
        return ResponseEntity.ok().body(btcService.fetchBtcPriceByFiat(type))
    }

    @PostMapping
    @CacheEvict("btc", allEntries = true, beforeInvocation = true)
    fun syncBtcPrice(): ResponseEntity<Void> {
        btcService.syncBtcPrice()
        return ResponseEntity.noContent().build()
    }

}
