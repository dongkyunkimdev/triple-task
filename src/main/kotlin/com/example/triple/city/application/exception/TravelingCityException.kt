package com.example.triple.city.application.exception

import com.example.triple.common.exception.BusinessException

class TravelingCityException(message: String) : BusinessException(message) {
    override val message = "해당 도시의 여행 정보가 존재합니다. : $message"
}