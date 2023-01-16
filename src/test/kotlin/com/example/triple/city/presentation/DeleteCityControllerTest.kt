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
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@AutoConfigureMockMvc
class DeleteCityControllerTest {
    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    @DisplayName("도시 정보 삭제 API 성공")
    @Transactional
    fun delete_city_success() {
        // given
        val result = postRequest("/city", hashMapOf("name" to "notDuplicatedName")).andReturn()
        val savedId = JsonPath.read<String>(result.response.contentAsString, "$.id")
        val existsIdDto = hashMapOf("id" to savedId)

        // when
        val actions = deleteRequest("/city", existsIdDto)

        // then
        actions
            .andExpectAll(
                MockMvcResultMatchers.status().isOk,
                MockMvcResultMatchers.jsonPath("name").value("notDuplicatedName")
            )
    }

    @Test
    @DisplayName("도시 정보 삭제 API 실패, id가 존재하지 않음")
    fun delete_city_fail_id_not_found() {
        // given
        val existsIdDto = hashMapOf("id" to "notExistsId")

        // when
        val expectedException = Assertions.assertThatThrownBy { deleteRequest("/city", existsIdDto) }

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

    private fun deleteRequest(requestUrl: String, content: Map<String, String>): ResultActions =
        mvc.perform(
            MockMvcRequestBuilders.delete(requestUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(content))
        )
}