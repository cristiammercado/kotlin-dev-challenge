package com.mcontigo.api.services

import com.mcontigo.api.dto.price.BTCPriceDTO
import com.mcontigo.api.model.enums.FiatType

interface BTCService {

    fun fetchAll(): List<BTCPriceDTO>

    fun fetchBtcPriceByFiat(type: FiatType): BTCPriceDTO

    fun syncBtcPrice()

}
