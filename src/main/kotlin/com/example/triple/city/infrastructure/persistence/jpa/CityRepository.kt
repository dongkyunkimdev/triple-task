package com.example.triple.city.infrastructure.persistence.jpa

import com.example.triple.city.domain.City
import org.springframework.data.jpa.repository.JpaRepository

interface CityRepository : JpaRepository<City, String> {
    fun findCityById(id: String): City?
}