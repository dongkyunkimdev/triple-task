package com.example.triple.city.infrastructure

import com.example.triple.city.application.CityPersistencePort
import com.example.triple.city.domain.City
import com.example.triple.city.domain.CityStatistic
import com.example.triple.city.infrastructure.persistence.jpa.CityRepository
import com.example.triple.city.infrastructure.persistence.jpa.CityStatisticRepository
import org.springframework.stereotype.Component

@Component
class CityPersistenceAdapter(
    private val cityRepository: CityRepository,
    private val cityStatisticRepository: CityStatisticRepository
) : CityPersistencePort {
    override fun findCityById(id: String): City? = cityRepository.findCityById(id)
    override fun existsCityByName(name: String): Boolean = cityRepository.existsCityByName(name)
    override fun save(city: City): City = cityRepository.save(city)
    override fun delete(city: City) = cityRepository.delete(city)
    override fun saveCityStatistic(cityStatistic: CityStatistic): CityStatistic =
        cityStatisticRepository.save(cityStatistic)

    override fun findCityStatisticByCityIdAndUserId(cityId: String, userId: String): CityStatistic? =
        cityStatisticRepository.findCityStatisticByCityIdAndUserId(cityId, userId)

    override fun findAllCity(): List<City> = cityRepository.findAll()
}