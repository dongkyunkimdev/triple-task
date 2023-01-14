package com.example.triple.city.application.exception

import com.example.triple.common.exception.BusinessException

class DuplicatedCityNameException(message: String) : BusinessException(message) {
    override val message = "이미 존재하는 도시의 이름입니다. : $message"
}