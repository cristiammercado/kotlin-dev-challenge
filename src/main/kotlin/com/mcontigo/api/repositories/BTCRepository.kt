package com.mcontigo.api.repositories

import com.mcontigo.api.model.BTCPrice
import com.mcontigo.api.model.enums.FiatType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface BTCRepository : JpaRepository<BTCPrice, FiatType>, JpaSpecificationExecutor<BTCPrice> {
}
