package com.mcontigo.api.client

import com.mcontigo.api.dto.coindesk.CoinDeskDTO
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(name = "coindesk-api", url = "\${clients.coindesk-api.url}")
interface CoinDeskClient {

    @GetMapping("/v1/bpi/currentprice.json", produces = ["application/javascript"])
    fun currentPrice(): CoinDeskDTO

}
