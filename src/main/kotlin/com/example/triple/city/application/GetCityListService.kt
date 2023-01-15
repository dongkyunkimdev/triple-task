package com.example.triple.city.application

import com.example.triple.city.domain.City
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetCityListService(
    private val cityPersistencePort: CityPersistencePort,
) {
    @Transactional
    fun command(): List<GetCityInfo> {
        val savedCityList = cityPersistencePort.findAllCity()

        return savedCityList.stream().map { GetCityInfo.from(it) }.toList()
    }

    data class GetCityInfo(
        val id: String,
        val name: String
    ) {
        companion object {
            fun from(city: City): GetCityInfo = GetCityInfo(
                id = city.getId(),
                name = city.name
            )
        }
    }
}