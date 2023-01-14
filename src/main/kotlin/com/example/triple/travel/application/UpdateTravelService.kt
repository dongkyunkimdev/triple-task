package com.example.triple.travel.application

import com.example.triple.city.application.CityPersistencePort
import com.example.triple.travel.application.dto.CityInfo
import com.example.triple.city.application.exception.CityNotFoundException
import com.example.triple.travel.application.exception.TravelNotFoundException
import com.example.triple.travel.domain.Travel
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class UpdateTravelService(
    private val travelPersistencePort: TravelPersistencePort,
    private val cityPersistencePort: CityPersistencePort
) {
    @Transactional
    fun command(command: UpdateTravelCommand): UpdateTravelInfo {
        val savedTravel = travelPersistencePort.findTravelById(command.id)
        savedTravel ?: throw TravelNotFoundException(command.id)
        val savedCity = cityPersistencePort.findCityById(command.cityId)
        savedCity ?: throw CityNotFoundException(command.cityId)
        savedTravel.update(command, savedCity)

        return UpdateTravelInfo.from(savedTravel)
    }

    data class UpdateTravelCommand(
        val id: String,
        val cityId: String,
        val startedAt: LocalDateTime,
        val endedAt: LocalDateTime
    )

    data class UpdateTravelInfo(
        val cityInfo: CityInfo,
        val id: String,
        val userId: String,
        val startedAt: LocalDateTime,
        val endedAt: LocalDateTime
    ) {
        companion object {
            fun from(travel: Travel): UpdateTravelInfo = UpdateTravelInfo(
                cityInfo = CityInfo.from(travel.city),
                id = travel.getId(),
                userId = travel.userId,
                startedAt = travel.startedAt,
                endedAt = travel.endedAt
            )
        }
    }
}