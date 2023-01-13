package com.example.triple.travel.application

import com.example.triple.city.application.CityPersistencePort
import com.example.triple.city.domain.City
import com.example.triple.travel.application.exception.CityNotFoundException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDateTime

class RegisterTravelServiceDescribeSpec : DescribeSpec({
    val mockTravelPersistencePort: TravelPersistencePort = mockk()
    val mockCityPersistencePort: CityPersistencePort = mockk()
    val registerTravelService = RegisterTravelService(mockTravelPersistencePort, mockCityPersistencePort)

    describe("여행 등록") {
        context("존재하지 않는 cityId를 가진 command가 주어지면") {
            every { mockCityPersistencePort.findCityById(notExistsCityIdCommand.cityId) } answers { null }
            it("CityNotFoundException 발생") {
                shouldThrow<CityNotFoundException> {
                    registerTravelService.command(notExistsCityIdCommand)
                }
            }
        }

        context("존재하는 cityId를 가지고") {
            every { mockCityPersistencePort.findCityById(existsCityIdCommand.cityId) } answers { City("cityName") }
            context("여행 출발일시가 종료일시 이후인 command가 주어지면") {
                it("IllegalArgumentException 발생") {
                    shouldThrow<IllegalArgumentException> {
                        registerTravelService.command(startedIsAfterEndedCommand)
                    }
                }
            }

        }
    }

}) {
    companion object {
        private val notExistsCityIdCommand = RegisterTravelService.RegisterTravelCommand(
            cityId = "notExistsId",
            startedAt = LocalDateTime.now().minusDays(1),
            endedAt = LocalDateTime.now(),
        )

        private val existsCityIdCommand = RegisterTravelService.RegisterTravelCommand(
            cityId = "existsId",
            startedAt = LocalDateTime.now().minusDays(1),
            endedAt = LocalDateTime.now(),
        )

        private val startedIsAfterEndedCommand = RegisterTravelService.RegisterTravelCommand(
            cityId = "existsId",
            startedAt = LocalDateTime.now().plusDays(1),
            endedAt = LocalDateTime.now(),
        )
    }
}