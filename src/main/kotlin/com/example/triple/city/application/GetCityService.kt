package com.example.triple.city.application

import com.example.triple.city.application.exception.CityNotFoundException
import com.example.triple.city.domain.City
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

        return GetCityInfo.from(savedCity)
    }

    data class GetCityCommand(
        val id: String
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