package com.example.triple.travel.presentation.dto

import com.example.triple.travel.application.dto.CityInfo

data class CityResponseDto(
    var id: String,
    var name: String
) {
    companion object {
        fun from(info: CityInfo): CityInfo = CityInfo(
            id = info.id,
            name = info.name
        )
    }
}