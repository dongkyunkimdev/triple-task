package com.example.triple.city.application

import com.example.triple.city.application.exception.CityNotFoundException
import com.example.triple.city.domain.City
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UpdateCityService(
    private val cityPersistencePort: CityPersistencePort
) {
    @Transactional
    fun command(command: UpdateCityCommand): UpdateCityInfo {
        val savedCity = cityPersistencePort.findCityById(command.id)
        savedCity ?: throw CityNotFoundException(command.id)
        savedCity.update(command)

        return UpdateCityInfo.from(savedCity)
    }

    data class UpdateCityCommand(
        val id: String,
        val name: String
    )

    data class UpdateCityInfo(
        val id: String,
        val name: String
    ) {
        companion object {
            fun from(city: City): UpdateCityInfo = UpdateCityInfo(
                id = city.getId(),
                name = city.name
            )
        }
    }
}