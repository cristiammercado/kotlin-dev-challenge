package com.mcontigo.api.dto.coindesk

import com.mcontigo.api.model.enums.FiatType

data class CoinDeskDTO(
    var time: TimeDTO? = TimeDTO(),
    var bpi: Map<FiatType, CurrencyDTO>? = emptyMap()
)
