package com.example.triple.travel.application

import com.example.triple.city.application.CityPersistencePort
import com.example.triple.city.domain.City
import com.example.triple.city.application.exception.CityNotFoundException
import com.example.triple.travel.application.exception.TravelNotFoundException
import com.example.triple.travel.domain.Travel
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDateTime

class UpdateTravelServiceDescribeSpec : DescribeSpec({
    val mockTravelPersistencePort: TravelPersistencePort = mockk()
    val mockCityPersistencePort: CityPersistencePort = mockk()
    val updateTravelService = UpdateTravelService(mockTravelPersistencePort, mockCityPersistencePort)

    describe("여행 정보 변경") {
        context("존재하지 않는 travelId를 가진 command가 주어지면") {
            every { mockTravelPersistencePort.findTravelById(notExistsTravelIdCommand.id) } answers { null }
            it("TravelNotFoundException 발생") {
                shouldThrow<TravelNotFoundException> {
                    updateTravelService.command(notExistsTravelIdCommand)
                }
            }
        }

        context("존재하는 travelId를 가지고") {
            context("존재하지 않는 cityId를 가진 command가 주어지면") {
                every { mockTravelPersistencePort.findTravelById("existsTravelId") } answers {
                    Travel(City("anyCityName"), notExistsCityIdCommand.startedAt, notExistsCityIdCommand.endedAt)
                }
                every { mockCityPersistencePort.findCityById("notExistsCityId") } answers { null }
                it("CityNotFoundException 발생") {
                    shouldThrow<CityNotFoundException> {
                        updateTravelService.command(notExistsCityIdCommand)
                    }
                }
            }

            context("존재하는 cityId를 가지며") {
                context("여행 출발일시가 종료일시 이후인 command가 주어지면") {
                    every { mockTravelPersistencePort.findTravelById("existsTravelId") } answers {
                        Travel(City("anyCityName"), startedIsAfterEndedCommand.startedAt, startedIsAfterEndedCommand.endedAt)
                    }
                    every { mockCityPersistencePort.findCityById("existsCityId") } answers { City("anyCityName") }
                    it("IllegalArgumentException 발생") {
                        shouldThrow<IllegalArgumentException> {
                            updateTravelService.command(startedIsAfterEndedCommand)
                        }
                    }
                }

                context("종료일시가 현재 일시의 이전인 command가 주어지면") {
                    every { mockTravelPersistencePort.findTravelById("existsTravelId") } answers {
                        Travel(City("anyCityName"), nowIsAfterEndedCommand.startedAt, nowIsAfterEndedCommand.endedAt)
                    }
                    every { mockCityPersistencePort.findCityById("existsCityId") } answers { City("anyCityName") }
                    it("IllegalArgumentException 발생") {
                        shouldThrow<IllegalArgumentException> {
                            updateTravelService.command(nowIsAfterEndedCommand)
                        }
                    }
                }
            }
        }
    }
}) {
    companion object {
        private val notExistsTravelIdCommand = UpdateTravelService.UpdateTravelCommand(
            id = "notExistsTravelId",
            cityId = "existsCityId",
            startedAt = LocalDateTime.now().minusDays(1),
            endedAt = LocalDateTime.now().plusDays(1)
        )

        private val notExistsCityIdCommand = UpdateTravelService.UpdateTravelCommand(
            id = "existsTravelId",
            cityId = "notExistsCityId",
            startedAt = LocalDateTime.now().minusDays(1),
            endedAt = LocalDateTime.now().plusDays(1)
        )

        private val startedIsAfterEndedCommand = UpdateTravelService.UpdateTravelCommand(
            id = "existsTravelId",
            cityId = "existsCityId",
            startedAt = LocalDateTime.now().plusDays(1),
            endedAt = LocalDateTime.now(),
        )

        private val nowIsAfterEndedCommand = UpdateTravelService.UpdateTravelCommand(
            id = "existsTravelId",
            cityId = "existsCityId",
            startedAt = LocalDateTime.now().minusDays(1),
            endedAt = LocalDateTime.now().minusMinutes(5),
        )
    }
}