package com.example.triple.city.presentation

import com.example.triple.city.application.GetCityListService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GetCityListController(
    private val getCityListService: GetCityListService
) {
    @GetMapping("/city-list")
    fun getCity(): ResponseEntity<List<GetCityResponseDto>> {
        val infoList = getCityListService.command()
        return ResponseEntity.ok(infoList.stream().map { GetCityResponseDto.from(it) }.toList())
    }

    data class GetCityResponseDto(
        val id: String,
        val name: String
    ) {
        companion object {
            fun from(info: GetCityListService.GetCityInfo): GetCityResponseDto = GetCityResponseDto(
                id = info.id,
                name = info.name
            )
        }
    }
}