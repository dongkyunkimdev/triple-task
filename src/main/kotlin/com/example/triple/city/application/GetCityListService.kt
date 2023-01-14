package com.example.triple.city.application

import com.example.triple.city.domain.City
import com.example.triple.travel.application.TravelPersistencePort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class GetCityListService(
    private val cityPersistencePort: CityPersistencePort,
    private val travelPersistencePort: TravelPersistencePort
) {
    @Transactional
    fun command(command: GetCityListCommand): List<GetCityInfo> {
        val getCityInfoList = mutableListOf<GetCityInfo>()

        val savedCityStatisticList =
            travelPersistencePort.findTravelByUserIdAndStartedAtGreaterThanEqualAndEndedAtLessThanEqual(
                command.userId,
                LocalDateTime.now(),
                LocalDateTime.now()
            )
        getCityInfoList.addAll(savedCityStatisticList.stream().map { GetCityInfo.from(it.city) }.toList())
//
//        travelPersistencePort.findTravelByUserId(command.userId)
//        val savedCity = cityPersistencePort.findCityById(command.id)
//        savedCity ?: throw CityNotFoundException(command.id)

        return getCityInfoList
    }

    data class GetCityListCommand(
        val userId: String
    )

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