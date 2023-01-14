package com.example.triple.travel.presentation

import com.example.triple.travel.application.UpdateTravelService
import com.example.triple.travel.application.dto.CityInfo
import com.example.triple.travel.presentation.dto.CityResponseDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class UpdateTravelController(
    private val updateTravelService: UpdateTravelService
) {
    @PatchMapping("/travel")
    fun updateTravel(@RequestBody dto: UpdateTravelRequestDto): ResponseEntity<UpdateTravelResponseDto> {
        val info = updateTravelService.command(dto.toCommand())
        return ResponseEntity.ok(UpdateTravelResponseDto.from(info))
    }

    data class UpdateTravelRequestDto(
        val id: String,
        val cityId: String,
        val startedAt: LocalDateTime,
        val endedAt: LocalDateTime
    ) {
        fun toCommand(): UpdateTravelService.UpdateTravelCommand = UpdateTravelService.UpdateTravelCommand(
            id = id,
            cityId = cityId,
            startedAt = startedAt,
            endedAt = endedAt,
        )
    }

    data class UpdateTravelResponseDto(
        val cityInfo: CityInfo,
        val id: String,
        val userId: String,
        val startedAt: LocalDateTime,
        val endedAt: LocalDateTime
    ) {
        companion object {
            fun from(info: UpdateTravelService.UpdateTravelInfo): UpdateTravelResponseDto =
                UpdateTravelResponseDto(
                    cityInfo = CityResponseDto.from(info.cityInfo),
                    id = info.id,
                    userId = info.userId,
                    startedAt = info.startedAt,
                    endedAt = info.endedAt
                )
        }
    }
}