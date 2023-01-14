package com.example.triple.travel.infrastructure.persistence.jpa

import com.example.triple.travel.domain.Travel
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface TravelRepository : JpaRepository<Travel, String> {
    fun existsTravelByCityId(id: String): Boolean
}