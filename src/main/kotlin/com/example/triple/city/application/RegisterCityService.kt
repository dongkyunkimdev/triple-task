package com.example.triple.city.application

import com.example.triple.city.application.exception.DuplicatedCityNameException
import com.example.triple.city.domain.City
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RegisterCityService(
    private val cityPersistencePort: CityPersistencePort
) {
    @Transactional
    fun command(command: RegisterCityCommand): RegisterCityInfo {
        if (cityPersistencePort.existsCityByName(command.name)) {
            throw DuplicatedCityNameException(command.name)
        }
        val savedCity = cityPersistencePort.save(command.toEntity())

        return RegisterCityInfo.from(savedCity)
    }

    data class RegisterCityCommand(
        val name: String
    ) {
        fun toEntity(): City = City(name)
    }

    data class RegisterCityInfo(
        val id: String,
        val name: String
    ) {
        companion object {
            fun from(city: City): RegisterCityInfo = RegisterCityInfo(
                id = city.getId(),
                name = city.name
            )
        }
    }
}