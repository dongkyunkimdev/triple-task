package com.example.triple.travel.application.exception

import com.example.triple.common.exception.BusinessException

class CityNotFoundException(message: String) : BusinessException(message) {
    override val message = "cityId가 존재하지 않습니다. : $message"
}