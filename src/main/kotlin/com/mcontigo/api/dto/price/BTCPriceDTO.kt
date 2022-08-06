package com.mcontigo.api.dto.price

import com.mcontigo.api.model.enums.FiatType
import java.math.BigDecimal
import java.time.LocalDateTime

data class BTCPriceDTO(val fiatType: FiatType, val price: BigDecimal, val lastUpdate: LocalDateTime)
