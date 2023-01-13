package com.example.triple.common.exception

open class BusinessException(override val message: String) : RuntimeException(message)