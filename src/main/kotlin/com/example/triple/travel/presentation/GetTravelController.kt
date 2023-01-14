package com.example.triple.travel.presentation

import com.example.triple.travel.application.GetTravelService
import com.example.triple.travel.application.dto.CityInfo
import com.example.triple.travel.presentation.dto.CityResponseDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class GetTravelController(
    private val getTravelService: GetTravelService
) {
    @GetMapping("/travel/{id}")
    fun getTravel(@PathVariable id: String): ResponseEntity<GetTravelResponseDto> {
        val info = getTravelService.command(GetTravelService.GetTravelCommand(id))
        return ResponseEntity.ok(GetTravelResponseDto.from(info))
    }

    data class GetTravelResponseDto(
        val cityInfo: CityInfo,
        val id: String,
        val userId: String,
        val startedAt: LocalDateTime,
        val endedAt: LocalDateTime
    ) {
        companion object {
            fun from(info: GetTravelService.GetTravelInfo): GetTravelResponseDto =
                GetTravelResponseDto(
                    cityInfo = CityResponseDto.from(info.cityInfo),
                    id = info.id,
                    userId = info.userId,
                    startedAt = info.startedAt,
                    endedAt = info.endedAt
                )
        }
    }
}