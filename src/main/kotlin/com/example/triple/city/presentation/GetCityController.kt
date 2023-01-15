package com.example.triple.city.presentation

import com.example.triple.city.application.GetCityService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class GetCityController(
    private val getCityService: GetCityService
) {
    @GetMapping("/city/{id}")
    fun getCity(@PathVariable id: String, @RequestParam userId: String): ResponseEntity<GetCityResponseDto> {
        val info = getCityService.command(GetCityService.GetCityCommand(id, userId))
        return ResponseEntity.ok(GetCityResponseDto.from(info))
    }

    data class GetCityResponseDto(
        val id: String,
        val name: String
    ) {
        companion object {
            fun from(info: GetCityService.GetCityInfo): GetCityResponseDto = GetCityResponseDto(
                id = info.id,
                name = info.name
            )
        }
    }
}