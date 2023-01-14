package com.example.triple.city.presentation

import com.example.triple.city.application.UpdateCityService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UpdateCityController(
    private val updateCityService: UpdateCityService
) {
    @PatchMapping("/city")
    fun updateCity(@RequestBody dto: UpdateCityRequestDto): ResponseEntity<UpdateCityResponseDto> {
        val info = updateCityService.command(dto.toCommand())
        return ResponseEntity.ok(UpdateCityResponseDto.from(info))
    }

    data class UpdateCityRequestDto(
        val id: String,
        val name: String
    ) {
        fun toCommand(): UpdateCityService.UpdateCityCommand = UpdateCityService.UpdateCityCommand(id, name)
    }

    data class UpdateCityResponseDto(
        val id: String,
        val name: String
    ) {
        companion object {
            fun from(info: UpdateCityService.UpdateCityInfo): UpdateCityResponseDto = UpdateCityResponseDto(
                id = info.id,
                name = info.name
            )
        }
    }
}