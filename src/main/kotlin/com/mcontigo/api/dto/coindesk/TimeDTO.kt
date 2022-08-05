package com.mcontigo.api.dto.coindesk

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

data class TimeDTO(
    @JsonProperty("updated") var updated: String? = null,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX") @JsonProperty("updatedISO") var updatedISO: LocalDateTime? = null,
    @JsonProperty("updateduk") var updateduk: String? = null
)
