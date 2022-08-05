package com.mcontigo.api.util

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.MediaType
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter
import org.springframework.stereotype.Component

@Component
class ApplicationJavascriptMessageConverter(objectMapper: ObjectMapper) :
    AbstractJackson2HttpMessageConverter(objectMapper) {

    init {
        setApplicationJavascriptMessageConverter()
    }

    private fun setApplicationJavascriptMessageConverter() {
        val supportedMediaTypes: MutableList<MediaType> = ArrayList()
        supportedMediaTypes.add(MediaType.APPLICATION_JSON)
        supportedMediaTypes.add(MediaType("application", "javascript"))
        setSupportedMediaTypes(supportedMediaTypes)
    }

}
