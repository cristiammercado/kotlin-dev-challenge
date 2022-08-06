package com.mcontigo.api.factory

import com.mcontigo.api.dto.coindesk.CoinDeskDTO
import com.mcontigo.api.dto.coindesk.CurrencyDTO
import com.mcontigo.api.dto.coindesk.TimeDTO
import com.mcontigo.api.model.enums.FiatType
import java.math.BigDecimal
import java.time.ZonedDateTime

class CoinDeskFactory {
    companion object {
        fun getInstance(): CoinDeskDTO {

            val time = TimeDTO().apply {
                updatedISO = ZonedDateTime.now()
            }

            val usd = CurrencyDTO().apply {
                code = FiatType.valueOf("USD")
                rateFloat = BigDecimal.valueOf(22995.40).setScale(2)
            }

            val gbp = CurrencyDTO().apply {
                code = FiatType.valueOf("GBP")
                rateFloat = BigDecimal.valueOf(19214.90).setScale(2)
            }

            val eur = CurrencyDTO().apply {
                code = FiatType.valueOf("EUR")
                rateFloat = BigDecimal.valueOf(22401.02).setScale(2)
            }

            return CoinDeskDTO().apply {
                this.time = time
                this.bpi = mapOf(
                    FiatType.USD to usd,
                    FiatType.GBP to gbp,
                    FiatType.EUR to eur,
                )
            }
        }
    }
}
