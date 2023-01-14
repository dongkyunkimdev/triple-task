package com.example.triple.city.infrastructure.persistence.jpa

import com.example.triple.city.domain.CityStatistic
import org.springframework.data.jpa.repository.JpaRepository

interface CityStatisticRepository : JpaRepository<CityStatistic, String> {
    fun findCityStatisticByCityIdAndUserId(cityId: String, userId: String): CityStatistic?
}