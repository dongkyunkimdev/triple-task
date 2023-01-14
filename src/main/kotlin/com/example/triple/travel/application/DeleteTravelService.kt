package com.example.triple.travel.application

import com.example.triple.travel.application.dto.CityInfo
import com.example.triple.travel.application.exception.TravelNotFoundException
import com.example.triple.travel.domain.Travel
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class DeleteTravelService(
    private val travelPersistencePort: TravelPersistencePort
) {
    @Transactional
    fun command(command: DeleteTravelCommand): DeleteTravelInfo {
        val savedTravel = travelPersistencePort.findTravelById(command.id)
        savedTravel ?: throw TravelNotFoundException(command.id)
        travelPersistencePort.deleteTravel(savedTravel)

        return DeleteTravelInfo.from(savedTravel)
    }

    data class DeleteTravelCommand(
        val id: String
    )

    data class DeleteTravelInfo(
        val cityInfo: CityInfo,
        val id: String,
        val userId: String,
        val startedAt: LocalDateTime,
        val endedAt: LocalDateTime
    ) {
        companion object {
            fun from(travel: Travel): DeleteTravelInfo = DeleteTravelInfo(
                cityInfo = CityInfo.from(travel.city),
                id = travel.getId(),
                userId = travel.userId,
                startedAt = travel.startedAt,
                endedAt = travel.endedAt
            )
        }
    }
}