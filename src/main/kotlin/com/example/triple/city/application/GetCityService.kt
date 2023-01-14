package com.example.triple.city.application

import com.example.triple.city.application.exception.CityNotFoundException
import com.example.triple.city.domain.City
import com.example.triple.city.domain.CityStatistic
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetCityService(
    private val cityPersistencePort: CityPersistencePort
) {
    @Transactional
    fun command(command: GetCityCommand): GetCityInfo {
        val savedCity = cityPersistencePort.findCityById(command.id)
        savedCity ?: throw CityNotFoundException(command.id)
        val newCityStatistic =
            cityPersistencePort.findCityStatisticByCityIdAndUserId(savedCity.getId(), command.userId)
                ?: CityStatistic(savedCity.getId(), savedCity.name, command.userId)
        val savedCityStatistic = cityPersistencePort.saveCityStatistic(newCityStatistic)
        savedCityStatistic.view()

        return GetCityInfo.from(savedCity)
    }

    data class GetCityCommand(
        val id: String,
        val userId: String
    )

    data class GetCityInfo(
        val id: String,
        val name: String
    ) {
        companion object {
            fun from(city: City): GetCityInfo = GetCityInfo(
                id = city.getId(),
                name = city.name
            )
        }
    }
}