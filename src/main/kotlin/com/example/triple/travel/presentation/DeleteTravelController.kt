package com.example.triple.travel.presentation

import com.example.triple.travel.application.DeleteTravelService
import com.example.triple.travel.application.dto.CityInfo
import com.example.triple.travel.presentation.dto.CityResponseDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class DeleteTravelController(
    private val deleteTravelService: DeleteTravelService
) {
    @DeleteMapping("/travel")
    fun deleteTravel(@RequestBody dto: DeleteTravelRequestDto): ResponseEntity<DeleteTravelResponseDto> {
        val info = deleteTravelService.command(dto.toCommand())
        return ResponseEntity.ok(DeleteTravelResponseDto.from(info))
    }

    data class DeleteTravelRequestDto(
        val id: String
    ) {
        fun toCommand(): DeleteTravelService.DeleteTravelCommand = DeleteTravelService.DeleteTravelCommand(
            id = id
        )
    }

    data class DeleteTravelResponseDto(
        val cityInfo: CityInfo,
        val id: String,
        val startedAt: LocalDateTime,
        val endedAt: LocalDateTime
    ) {
        companion object {
            fun from(info: DeleteTravelService.DeleteTravelInfo): DeleteTravelResponseDto =
                DeleteTravelResponseDto(
                    cityInfo = CityResponseDto.from(info.cityInfo),
                    id = info.id,
                    startedAt = info.startedAt,
                    endedAt = info.endedAt
                )
        }
    }
}