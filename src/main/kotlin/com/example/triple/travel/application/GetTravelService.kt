package com.example.triple.travel.application

import com.example.triple.travel.application.dto.CityInfo
import com.example.triple.travel.application.exception.TravelNotFoundException
import com.example.triple.travel.domain.Travel
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class GetTravelService(
    private val travelPersistencePort: TravelPersistencePort
) {
    @Transactional(readOnly = true)
    fun command(command: GetTravelCommand): GetTravelInfo {
        val savedTravel = travelPersistencePort.findTravelById(command.id)
        savedTravel ?: throw TravelNotFoundException(command.id)

        return GetTravelInfo.from(savedTravel)
    }

    data class GetTravelCommand(
        val id: String
    )

    data class GetTravelInfo(
        val cityInfo: CityInfo,
        val id: String,
        val startedAt: LocalDateTime,
        val endedAt: LocalDateTime
    ) {
        companion object {
            fun from(travel: Travel): GetTravelInfo = GetTravelInfo(
                cityInfo = CityInfo.from(travel.city),
                id = travel.getId(),
                startedAt = travel.startedAt,
                endedAt = travel.endedAt
            )
        }
    }
}