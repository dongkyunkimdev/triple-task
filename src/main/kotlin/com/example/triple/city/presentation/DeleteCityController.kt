package com.example.triple.city.presentation

import com.example.triple.city.application.DeleteCityService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class DeleteCityController(
    private val deleteCityService: DeleteCityService
) {
    @DeleteMapping("/city")
    fun deleteCity(@RequestBody dto: DeleteCityRequestDto): ResponseEntity<DeleteCityResponseDto> {
        val info = deleteCityService.command(dto.toCommand())
        return ResponseEntity.ok(DeleteCityResponseDto.from(info))
    }

    data class DeleteCityRequestDto(
        val id: String
    ) {
        fun toCommand(): DeleteCityService.DeleteCityCommand = DeleteCityService.DeleteCityCommand(id)
    }

    data class DeleteCityResponseDto(
        val id: String,
        val name: String
    ) {
        companion object {
            fun from(info: DeleteCityService.DeleteCityInfo): DeleteCityResponseDto = DeleteCityResponseDto(
                id = info.id,
                name = info.name
            )
        }
    }
}