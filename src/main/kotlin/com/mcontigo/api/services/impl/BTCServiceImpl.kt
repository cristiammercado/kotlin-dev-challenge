package com.mcontigo.api.services.impl

import com.mcontigo.api.client.CoinDeskClient
import com.mcontigo.api.dto.coindesk.CoinDeskMapper
import com.mcontigo.api.dto.price.BTCPriceDTO
import com.mcontigo.api.dto.price.BTCPriceMapper
import com.mcontigo.api.model.enums.FiatType
import com.mcontigo.api.repositories.BTCRepository
import com.mcontigo.api.services.BTCService
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class BTCServiceImpl(
    private val repository: BTCRepository,
    private val client: CoinDeskClient,
    private val coinDeskMapper: CoinDeskMapper,
    private val btcPriceMapper: BTCPriceMapper
) : BTCService {

    override fun fetchAll(): List<BTCPriceDTO> {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "lastUpdate"))
            .map(btcPriceMapper::toDto)
    }

    override fun fetchBtcPriceByFiat(type: FiatType): BTCPriceDTO {
        return repository.findById(type)
            .map(btcPriceMapper::toDto)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND) }
    }

    override fun syncBtcPrice() {
        val dto = client.currentPrice()
        val list = coinDeskMapper.toListModel(dto)
        repository.saveAll(list)
    }

}
