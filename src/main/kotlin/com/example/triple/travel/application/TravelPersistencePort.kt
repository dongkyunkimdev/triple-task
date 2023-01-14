package com.example.triple.travel.application

import com.example.triple.travel.domain.Travel
import java.time.LocalDateTime

interface TravelPersistencePort {
    fun save(travel: Travel): Travel
    fun findTravelById(id: String): Travel?
    fun deleteTravel(savedTravel: Travel)
    fun existsTravelByCityId(id: String): Boolean
    fun findTravelByUserId(userId: String): List<Travel>
    fun findTravelByUserIdAndStartedAtGreaterThanEqualAndEndedAtLessThanEqual(
        userId: String,
        now: LocalDateTime,
        now1: LocalDateTime
    ): List<Travel>
}