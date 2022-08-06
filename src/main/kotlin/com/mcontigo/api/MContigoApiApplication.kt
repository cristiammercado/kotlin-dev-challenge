package com.mcontigo.api

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableCaching
@EnableFeignClients
class MContigoApiApplication

fun main(args: Array<String>) {
    runApplication<MContigoApiApplication>(*args) {
        setBannerMode(Banner.Mode.OFF)
    }
}
