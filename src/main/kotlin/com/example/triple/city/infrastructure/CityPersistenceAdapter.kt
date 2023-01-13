package com.example.triple.city.infrastructure

import com.example.triple.city.application.CityPersistencePort
import com.example.triple.city.domain.City
import com.example.triple.city.infrastructure.persistence.jpa.CityRepository
import org.springframework.stereotype.Component

@Component
class CityPersistenceAdapter(
    private val cityRepository: CityRepository
) : CityPersistencePort {
    override fun findCityById(id: String): City? = cityRepository.findCityById(id)
}