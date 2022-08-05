package com.mcontigo.api.services.impl

import com.mcontigo.api.client.CoinDeskClient
import com.mcontigo.api.dto.coindesk.CoinDeskMapper
import com.mcontigo.api.dto.price.BTCPriceDTO
import com.mcontigo.api.dto.price.BTCPriceMapper
import com.mcontigo.api.model.enums.FiatType
import com.mcontigo.api.repositories.BTCRepository
import com.mcontigo.api.services.BTCService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime

@Service
class BTCServiceImpl(
    private val repository: BTCRepository,
    private val client: CoinDeskClient,
    private val coinDeskMapper: CoinDeskMapper,
    private val btcPriceMapper: BTCPriceMapper
) : BTCService {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun fetchAll(): List<BTCPriceDTO> {
        return repository.findAll()
            .map(btcPriceMapper::toDto)
    }

    override fun fetchBtcPriceByFiat(type: FiatType): BTCPriceDTO {
        return repository.findById(type)
            .map(btcPriceMapper::toDto)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND) }
    }

    @Async
    override fun syncBtcPrice() {
        logger.info("Started sync of BTC price at {}", LocalDateTime.now())
        val dto = client.currentPrice()
        val list = coinDeskMapper.toListModel(dto)
        repository.saveAll(list)
        logger.info("Finished sync of BTC price at {}", LocalDateTime.now())
    }

}
