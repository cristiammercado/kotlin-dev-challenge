package com.mcontigo.api.controllers

import com.mcontigo.api.config.CoinDeskMock
import com.mcontigo.api.factory.CoinDeskFactory
import com.mcontigo.api.model.BTCPrice
import com.mcontigo.api.model.enums.FiatType
import com.mcontigo.api.repositories.BTCRepository
import net.datafaker.Faker
import org.hamcrest.Matchers
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import java.math.BigDecimal
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BTCControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var btcRepository: BTCRepository

    private val faker: Faker = Faker()

    private val coinDeskMock = CoinDeskMock(8092)

    @BeforeAll
    fun loadMock() {
        coinDeskMock.startMockServer()
    }

    @AfterAll
    fun shutDownMock() {
        coinDeskMock.stop()
    }

    @Test
    fun testFetchAll() {
        val dto = CoinDeskFactory.getInstance()

        val element1 = BTCPrice().apply {
            fiatType = FiatType.EUR
            price = dto.bpi?.get(FiatType.EUR)?.rateFloat
            lastUpdate = ZonedDateTime.now()
        }

        val element2 = BTCPrice().apply {
            fiatType = FiatType.USD
            price = dto.bpi?.get(FiatType.USD)?.rateFloat
            lastUpdate = ZonedDateTime.now().plusSeconds(1)
        }

        val element3 = BTCPrice().apply {
            fiatType = FiatType.GBP
            price = dto.bpi?.get(FiatType.GBP)?.rateFloat
            lastUpdate = ZonedDateTime.now().plusSeconds(2)
        }

        btcRepository.save(element1)
        btcRepository.save(element2)
        btcRepository.save(element3)

        this.mockMvc.perform(get("/"))
            .andDo(print())
            .andExpectAll(
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("$[0].fiatType", Matchers.equalTo(element3.fiatType.toString())),
                jsonPath("$[0].price", Matchers.equalTo(element3.price?.toDouble())),
                jsonPath(
                    "$[0].lastUpdate",
                    Matchers.equalTo(element3.lastUpdate?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")))
                ),
                jsonPath("$[1].fiatType", Matchers.equalTo(element2.fiatType.toString())),
                jsonPath("$[1].price", Matchers.equalTo(element2.price?.toDouble())),
                jsonPath(
                    "$[1].lastUpdate",
                    Matchers.equalTo(element2.lastUpdate?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")))
                ),
                jsonPath("$[2].fiatType", Matchers.equalTo(element1.fiatType.toString())),
                jsonPath("$[2].price", Matchers.equalTo(element1.price?.toDouble())),
                jsonPath(
                    "$[2].lastUpdate",
                    Matchers.equalTo(element1.lastUpdate?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")))
                ),
            )

    }

    @Test
    fun fetchBtcPriceByFiat() {
        val element = BTCPrice().apply {
            fiatType = FiatType.USD
            price = BigDecimal.valueOf(faker.number().numberBetween(10000L, 99000L), 2)
            lastUpdate = ZonedDateTime.now().plusSeconds(1)
        }

        btcRepository.save(element)

        this.mockMvc.perform(get("/pair/USD"))
            .andDo(print())
            .andExpectAll(
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("$.fiatType", Matchers.equalTo(element.fiatType.toString())),
                jsonPath("$.price", Matchers.equalTo(element.price?.toDouble())),
                jsonPath(
                    "$.lastUpdate",
                    Matchers.equalTo(element.lastUpdate?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")))
                )
            )

    }

    @Test
    fun syncBtcPrice() {
        this.mockMvc.perform(post("/"))
            .andDo(print())
            .andExpectAll(MockMvcResultMatchers.status().isNoContent)

        val list = btcRepository.findAll()

        assertNotNull(list)
        assertEquals(3, list.size)
    }

}
