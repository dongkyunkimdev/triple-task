package com.example.triple.travel.infrastructure.persistence

import com.example.triple.travel.application.TravelPersistencePort
import com.example.triple.travel.domain.Travel
import com.example.triple.travel.infrastructure.persistence.jpa.TravelRepository

class TravelPersistenceAdapter(
    private val travelRepository: TravelRepository
) : TravelPersistencePort {
    override fun save(travel: Travel): Travel = travelRepository.save(travel)
}