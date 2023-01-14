package com.example.triple.travel.application

import com.example.triple.city.application.CityPersistencePort
import com.example.triple.city.domain.City
import com.example.triple.travel.application.dto.CityInfo
import com.example.triple.city.application.exception.CityNotFoundException
import com.example.triple.travel.domain.Travel
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class RegisterTravelService(
    private val travelPersistencePort: TravelPersistencePort,
    private val cityPersistencePort: CityPersistencePort
) {
    @Transactional
    fun command(command: RegisterTravelCommand): RegisterTravelInfo {
        val savedCity = cityPersistencePort.findCityById(command.cityId)
        savedCity ?: throw CityNotFoundException(command.cityId)
        val newTravel = createTravel(savedCity, command)
        val savedTravel = travelPersistencePort.save(newTravel)

        return RegisterTravelInfo.from(savedTravel)
    }

    private fun createTravel(
        savedCity: City,
        command: RegisterTravelCommand
    ): Travel = Travel(
        city = savedCity,
        startedAt = command.startedAt,
        endedAt = command.endedAt
    )

    data class RegisterTravelCommand(
        val cityId: String,
        val startedAt: LocalDateTime,
        val endedAt: LocalDateTime
    )

    data class RegisterTravelInfo(
        val cityInfo: CityInfo,
        val id: String,
        val startedAt: LocalDateTime,
        val endedAt: LocalDateTime
    ) {
        companion object {
            fun from(travel: Travel): RegisterTravelInfo = RegisterTravelInfo(
                cityInfo = CityInfo.from(travel.city),
                id = travel.getId(),
                startedAt = travel.startedAt,
                endedAt = travel.endedAt
            )
        }
    }
}