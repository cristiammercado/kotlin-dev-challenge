package com.mcontigo.api.dto.coindesk

import com.fasterxml.jackson.annotation.JsonProperty
import com.mcontigo.api.model.enums.FiatType
import java.math.BigDecimal

data class CurrencyDTO(
    @JsonProperty("code") var code: FiatType? = null,
    @JsonProperty("symbol") var symbol: String? = null,
    @JsonProperty("rate") var rate: String? = null,
    @JsonProperty("description") var description: String? = null,
    @JsonProperty("rate_float") var rateFloat: BigDecimal? = null
)
