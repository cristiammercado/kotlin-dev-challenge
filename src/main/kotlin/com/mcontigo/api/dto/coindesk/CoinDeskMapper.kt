package com.mcontigo.api.dto.coindesk

import com.mcontigo.api.model.BTCPrice
import com.mcontigo.api.model.enums.FiatType
import org.mapstruct.*

@Mapper(
    componentModel = "spring",
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
abstract class CoinDeskMapper {

    fun toListModel(coinDeskDTO: CoinDeskDTO): MutableList<BTCPrice> {
        val list = mutableListOf<BTCPrice>()

        coinDeskDTO.bpi!!.entries.forEach {
            list.add(toModel(coinDeskDTO.time!!, it.value))
        }

        return list
    }

    @Mappings(
        value = [
            Mapping(source = "currencyDTO.code", target = "fiatType"),
            Mapping(source = "currencyDTO.rateFloat", target = "price"),
            Mapping(source = "timeDTO.updatedISO", target = "lastUpdate")
        ]
    )
    abstract fun toModel(timeDTO: TimeDTO, currencyDTO: CurrencyDTO): BTCPrice

}
