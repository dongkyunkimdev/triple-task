package com.example.triple.city.application

import com.example.triple.city.application.exception.CityNotFoundException
import com.example.triple.city.domain.City
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DeleteCityService(
    private val cityPersistencePort: CityPersistencePort
) {
    @Transactional
    fun command(command: DeleteCityCommand): DeleteCityInfo {
        val savedCity = cityPersistencePort.findCityById(command.id)
        savedCity ?: throw CityNotFoundException(command.id)
        cityPersistencePort.delete(savedCity)

        return DeleteCityInfo.from(savedCity)
    }

    data class DeleteCityCommand(
        val id: String
    )

    data class DeleteCityInfo(
        val id: String,
        val name: String
    ) {
        companion object {
            fun from(city: City): DeleteCityInfo = DeleteCityInfo(
                id = city.getId(),
                name = city.name
            )
        }
    }
}