package com.example.triple.city.presentation

import com.fasterxml.jackson.databind.ObjectMapper
import com.jayway.jsonpath.JsonPath
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
class GetCityControllerTest {
    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    @DisplayName("도시 단일 정보 조회 API 성공")
    fun get_city_success() {
        // given
        val result = postRequest("/city", hashMapOf("name" to "notDuplicatedName")).andReturn()
        val savedId = JsonPath.read<String>(result.response.contentAsString, "$.id")

        // when
        val actions = getRequest("/city/$savedId?userId=anyUser")

        // then
        actions
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    private fun getRequest(requestUrl: String): ResultActions =
        mvc.perform(
            MockMvcRequestBuilders.get(requestUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )

    private fun postRequest(requestUrl: String, content: Map<String, String>): ResultActions =
        mvc.perform(
            MockMvcRequestBuilders.post(requestUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(content))
        )
}