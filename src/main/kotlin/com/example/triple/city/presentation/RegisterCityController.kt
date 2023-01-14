package com.example.triple.city.presentation

import com.example.triple.city.application.RegisterCityService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class RegisterCityController(
    private val registerCityService: RegisterCityService
) {
    @PostMapping("/city")
    fun registerCity(@RequestBody dto: RegisterCityRequestDto): ResponseEntity<RegisterCityResponseDto> {
        val info = registerCityService.command(dto.toCommand())
        return ResponseEntity(RegisterCityResponseDto.from(info), HttpStatus.CREATED)
    }

    data class RegisterCityRequestDto(
        val name: String
    ) {
        fun toCommand(): RegisterCityService.RegisterCityCommand = RegisterCityService.RegisterCityCommand(name)
    }

    data class RegisterCityResponseDto(
        val id: String,
        val name: String
    ) {
        companion object {
            fun from(info: RegisterCityService.RegisterCityInfo): RegisterCityResponseDto = RegisterCityResponseDto(
                id = info.id,
                name = info.name
            )
        }
    }
}