package com.example.triple.travel.presentation

import com.example.triple.travel.application.RegisterTravelService
import com.example.triple.travel.application.dto.CityInfo
import com.example.triple.travel.presentation.dto.CityResponseDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class RegisterTravelController(
    private val registerTravelService: RegisterTravelService
) {
    @PostMapping("/travel")
    fun registerTravel(@RequestBody dto: RegisterTravelRequestDto): ResponseEntity<RegisterTravelResponseDto> {
        val info = registerTravelService.command(dto.toCommand())
        return ResponseEntity(RegisterTravelResponseDto.from(info), HttpStatus.CREATED)
    }

    data class RegisterTravelRequestDto(
        val cityId: String,
        val startedAt: LocalDateTime,
        val endedAt: LocalDateTime
    ) {
        fun toCommand(): RegisterTravelService.RegisterTravelCommand = RegisterTravelService.RegisterTravelCommand(
            cityId = cityId,
            startedAt = startedAt,
            endedAt = endedAt,
        )
    }

    data class RegisterTravelResponseDto(
        val cityInfo: CityInfo,
        val id: String,
        val startedAt: LocalDateTime,
        val endedAt: LocalDateTime
    ) {
        companion object {
            fun from(info: RegisterTravelService.RegisterTravelInfo): RegisterTravelResponseDto =
                RegisterTravelResponseDto(
                    cityInfo = CityResponseDto.from(info.cityInfo),
                    id = info.id,
                    startedAt = info.startedAt,
                    endedAt = info.endedAt
                )
        }
    }
}