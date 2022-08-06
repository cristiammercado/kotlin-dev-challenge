package com.mcontigo.api.dto.price

import com.mcontigo.api.model.BTCPrice
import org.mapstruct.*

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
interface BTCPriceMapper {

    @Mappings
    fun toDto(btcPrice: BTCPrice): BTCPriceDTO
}
