package com.mcontigo.api.dto.coindesk

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.format.annotation.DateTimeFormat
import java.time.ZonedDateTime

data class TimeDTO(
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX") @JsonProperty("updatedISO") var updatedISO: ZonedDateTime? = null
)
