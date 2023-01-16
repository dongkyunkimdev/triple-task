package com.example.triple.city.presentation

import com.example.triple.city.application.exception.DuplicatedCityNameException
import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class RegisterCityControllerTest {
    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    @DisplayName("도시 등록 API 성공")
    fun register_city_success() {
        // given
        val notDuplicatedDto = hashMapOf("name" to "notDuplicatedName")

        // when
        val actions = postRequest("/city", notDuplicatedDto)

        // then
        actions
            .andExpectAll(
                status().isCreated,
                jsonPath("name").value(notDuplicatedDto["name"])
            )
    }

    @Test()
    @DisplayName("도시 등록에 실패, 이름이 중복됨")
    fun register_city_fail_duplicated_name() {
        // given
        val duplicatedDto = hashMapOf("name" to "duplicatedName")
        postRequest("/city", duplicatedDto)

        // when
        val expectedException = assertThatThrownBy { postRequest("/city", duplicatedDto) }

        // then
        expectedException.cause.isInstanceOf(DuplicatedCityNameException::class.java)
    }

    private fun postRequest(requestUrl: String, content: Map<String, String>): ResultActions =
        mvc.perform(
            post(requestUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(content))
        )
}