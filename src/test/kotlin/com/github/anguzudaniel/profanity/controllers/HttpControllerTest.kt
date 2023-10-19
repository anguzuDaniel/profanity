package com.github.anguzudaniel.profanity.controllers

import com.github.anguzudaniel.profanity.entity.Suggestion
import com.github.anguzudaniel.profanity.entity.SuggestionResponse
import com.github.anguzudaniel.profanity.repositories.SuggestionResponseRepository
import com.github.anguzudaniel.profanity.services.SuggestionResponseService
import com.github.anguzudaniel.profanity.services.SuggestionService
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.hasSize
import org.json.JSONArray
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.util.*

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class HttpControllerTest(@Autowired val mockMvc: MockMvc) {

    private val suggestionService = mockk<SuggestionService>()
    private val suggestionResponseService = mockk<SuggestionResponseService>()

    @Autowired
    private lateinit var suggestionResponseRepository: SuggestionResponseRepository

    private var URL = "What%20the%20fuck%20are%20you%20saying"

    @BeforeEach
    fun setUp() {
        // Insert test data into the H2 database
        val suggestedResponse = SuggestionResponse(
            id = 1,
            responseText = "Great job! Keep it u",
            createdBy = "Admin",
            suggestion = null
        )
        val suggestedResponse1 = SuggestionResponse(
            id = 2,
            responseText = "Interesting idea. I like it.",
            createdBy = "Admin",
            suggestion = null
        )

        suggestionResponseRepository.saveAll(listOf(suggestedResponse, suggestedResponse1))
    }

    @Test
    fun `get suggestion response`() {
        val suggestedResponse = SuggestionResponse(
            id = 1,
            responseText = "Great job! Keep it u",
            createdBy = "Admin",
            suggestion = null
        )
        val suggestedResponse1 = SuggestionResponse(
            id = 2,
            responseText = "Interesting idea. I like it.",
            createdBy = "Admin",
            suggestion = null
        )

        every { suggestionResponseService.getSuggestionResponse() } returns listOf(
            suggestedResponse,
            suggestedResponse1
        )

        val responseContent = mockMvc
            .perform(
                get("/api/v1/profanity/")
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn().response.contentAsString

        val jsonArray = JSONArray(responseContent)

        println(jsonArray)

        assertThat(jsonArray.getJSONObject(0).get("responseText")).isEqualTo("Great job! Keep it up.")
    }

    @Test
    fun `get suggestions by number`() {
        val num = 2

        val suggestedResponse = SuggestionResponse(
            id = 1,
            responseText = "Great job! Keep it up.",
            createdBy = "Admin",
            suggestion = null
        )
        val suggestedResponse1 = SuggestionResponse(
            id = 2,
            responseText = "Interesting idea. I like it.",
            createdBy = "Admin",
            suggestion = null
        )

        every { suggestionResponseService.getLimitedNumberOfSuggestions(num) } returns listOf(
            suggestedResponse,
            suggestedResponse1
        )

        mockMvc.perform(
            get("/api/v1/profanity/$num")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize<Int>(num)))
    }

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

        mockMvc.perform(
            get("/api/v1/profanity/filter/single/{text:.+}", "What the fuck")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$['profaneWordsFound']").value(profaneWord))
    }
}