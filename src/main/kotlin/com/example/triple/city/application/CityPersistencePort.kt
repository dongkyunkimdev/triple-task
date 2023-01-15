package com.example.triple.city.application

import com.example.triple.city.domain.City
import com.example.triple.city.domain.CityStatistic

interface CityPersistencePort {
    fun findCityById(id: String): City?
    fun existsCityByName(name: String): Boolean
    fun save(city: City): City
    fun delete(city: City)
    fun saveCityStatistic(cityStatistic: CityStatistic): CityStatistic
    fun findCityStatisticByCityIdAndUserId(cityId: String, userId: String): CityStatistic?
    fun findAllCity(): List<City>
}