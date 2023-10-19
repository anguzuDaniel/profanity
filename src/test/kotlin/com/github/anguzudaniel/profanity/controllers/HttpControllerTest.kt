package com.github.anguzudaniel.profanity.controllers

import com.github.anguzudaniel.profanity.entity.Suggestion
import com.github.anguzudaniel.profanity.repositories.SuggestionResponseRepository
import com.github.anguzudaniel.profanity.services.SuggestionService
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.json.JSONArray
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.util.*

@AutoConfigureMockMvc
@SpringBootTest
class HttpControllerTest(@Autowired val mockMvc: MockMvc) {

    private val suggestionService = mockk<SuggestionService>()

    @MockBean
    lateinit var suggestionRepository: SuggestionResponseRepository

    private var URL = "What%20the%20fuck%20are%20you%20saying"

    @Test
    fun `filter text with multiple suggestions`() {
        val suggestion = Suggestion(id = UUID.randomUUID(), "Inappropriate language detected: fuck")
        val suggestion1 = Suggestion(id = UUID.randomUUID(), "Inappropriate language detected: fuck")

        every { suggestionService.filterSuggestions("What the fuck") } returns listOf(suggestion, suggestion1)

        val responseContent = mockMvc.perform(
            get("/api/v1/profanity/filter/multiple/{text:.+}", URL)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn().response.contentAsString

        val jsonArray = JSONArray(responseContent) // Parse the response as a JSONArray

        assertThat(jsonArray.getJSONObject(0).getString("text")).isEqualTo(suggestion.text)
    }

    @Test
    fun `filter text with single suggestion`() {
        val profaneWord = "fuck"

        every { suggestionService.getProfaneWords(URL) } returns listOf(profaneWord)

        val responseContent = mockMvc.perform(
            get("/api/v1/profanity/filter/single/{text:.+}", "What the fuck")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$['profaneWordsFound']").value(profaneWord))
    }
}