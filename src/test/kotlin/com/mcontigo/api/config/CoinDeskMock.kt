package com.mcontigo.api.config

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import org.springframework.core.io.ClassPathResource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus

class CoinDeskMock(private val port: Int) : WireMockServer(port) {

    fun startMockServer() {
        start()

        stubFor(
            WireMock.get("/v1/bpi/currentprice.json")
                .willReturn(
                    WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/javascript")
                        .withBody(ClassPathResource("mock/coindesk.json").file.readText())
                )
        )

    }
}
