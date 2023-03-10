package com.example.triple.travel.application

import com.example.triple.travel.domain.Travel

interface TravelPersistencePort {
    fun save(travel: Travel): Travel
    fun findTravelById(id: String): Travel?
    fun deleteTravel(savedTravel: Travel)
    fun existsTravelByCityId(id: String): Boolean
}