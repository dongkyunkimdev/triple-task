package com.example.triple.travel.infrastructure.persistence

import com.example.triple.travel.application.TravelPersistencePort
import com.example.triple.travel.domain.Travel
import com.example.triple.travel.infrastructure.persistence.jpa.TravelRepository
import com.example.triple.travel.infrastructure.persistence.querydsl.TravelRepositorySupport
import org.springframework.stereotype.Component

@Component
class TravelPersistenceAdapter(
    private val travelRepository: TravelRepository,
    private val travelRepositorySupport: TravelRepositorySupport
) : TravelPersistencePort {
    override fun save(travel: Travel): Travel = travelRepository.save(travel)
    override fun findTravelById(id: String): Travel? = travelRepositorySupport.findTravelById(id)
    override fun deleteTravel(savedTravel: Travel) = travelRepository.delete(savedTravel)
    override fun existsTravelByCityId(id: String): Boolean = travelRepository.existsTravelByCityId(id)
    override fun findTravelByUserId(userId: String): List<Travel> = travelRepositorySupport.findTravelByUserId(userId)
    override fun findTravelByUserIdAndBetweenStartedAtAndEndedAt(userId: String): List<Travel> =
        travelRepositorySupport.findTravelByUserIdAndStartedAtGreaterThanEqualAndEndedAtLessThanEqual(userId)
}