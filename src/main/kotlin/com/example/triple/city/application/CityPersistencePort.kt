package com.example.triple.city.application

import com.example.triple.city.domain.City

interface CityPersistencePort {
    fun findCityById(id: String): City?
}