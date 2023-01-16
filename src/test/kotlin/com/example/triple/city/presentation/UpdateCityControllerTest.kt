package com.example.triple.city.presentation

import com.example.triple.city.application.exception.CityNotFoundException
import com.fasterxml.jackson.databind.ObjectMapper
import com.jayway.jsonpath.JsonPath
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
class UpdateCityControllerTest {
    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    @DisplayName("도시 정보 수정 API 성공")
    fun update_city_success() {
        // given
        val result = postRequest("/city", hashMapOf("name" to "notDuplicatedName")).andReturn()
        val savedId = JsonPath.read<String>(result.response.contentAsString, "$.id")
        val existsIdDto = hashMapOf("id" to savedId, "name" to "updatedName")

        // when
        val actions = patchRequest("/city", existsIdDto)

        // then
        actions
            .andExpectAll(
                MockMvcResultMatchers.status().isOk,
                MockMvcResultMatchers.jsonPath("name").value(existsIdDto["name"])
            )
    }

    @Test
    @DisplayName("도시 정보 수정 API 실패, id가 존재하지 않음")
    fun update_city_fail_id_not_found() {
        // given
        val notExistsIdDto = hashMapOf("id" to "notExistsId", "name" to "updatedName")

        // when
        val expectedException = Assertions.assertThatThrownBy { patchRequest("/city", notExistsIdDto) }

        // then
        expectedException.cause.isInstanceOf(CityNotFoundException::class.java)
    }

    private fun postRequest(requestUrl: String, content: Map<String, String>): ResultActions =
        mvc.perform(
            MockMvcRequestBuilders.post(requestUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(content))
        )

    private fun patchRequest(requestUrl: String, content: Map<String, String>): ResultActions =
        mvc.perform(
            MockMvcRequestBuilders.patch(requestUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(content))
        )
}