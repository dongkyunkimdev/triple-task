package com.example.triple.travel.application.dto

import com.example.triple.city.domain.City

data class CityInfo(
    var id: String,
    var name: String
) {
    companion object {
        fun from(city: City): CityInfo = CityInfo(
            id = city.getId(),
            name = city.name
        )
    }
}