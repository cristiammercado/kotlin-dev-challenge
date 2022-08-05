package com.mcontigo.api.model

import com.mcontigo.api.model.enums.FiatType
import org.hibernate.annotations.DynamicUpdate
import org.springframework.data.annotation.LastModifiedDate
import java.io.Serializable
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*

@Table(name = "btc_prices")
@Entity
@DynamicUpdate
class BTCPrice(

    @Id
    @Column(name = "fiat_type")
    @Enumerated(EnumType.STRING)
    var fiatType: FiatType? = null,

    @Column
    var price: BigDecimal? = null,

    @Column(name = "last_update")
    @LastModifiedDate
    var lastUpdate: LocalDateTime? = LocalDateTime.now()

) : Serializable
