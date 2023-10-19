package com.github.anguzudaniel.profanity.controllers

import com.github.anguzudaniel.profanity.entity.Suggestion
import com.github.anguzudaniel.profanity.entity.SuggestionResponse
import com.github.anguzudaniel.profanity.services.SuggestionResponseService
import com.github.anguzudaniel.profanity.services.SuggestionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/v1/profanity")
class HttpController(
    private val suggestionService: SuggestionService,
    private val suggestionResponseService: SuggestionResponseService
) {
    @GetMapping("/")
    fun getSuggestions(): List<SuggestionResponse> = suggestionResponseService.getSuggestionResponse()

    /**
     * getting a limited number of suggestion responses
     * @param num get the number specified from the url
     */
    @GetMapping("/{num}")
    fun getSuggestionsByNumber(@PathVariable num: Int): ResponseEntity<List<SuggestionResponse>> {
        val suggestions = suggestionResponseService.getLimitedNumberOfSuggestions(num)
        return ResponseEntity.ok(suggestions)
    }

    @PostMapping("/suggestions")
    fun getSuggestionResponse(): List<SuggestionResponse> = suggestionResponseService.getSuggestionResponse()

    // returns multiple json objects of profane words are found
    @GetMapping("/filter/multiple/{text:.+}")
    fun filterTextWithMultipleSuggestions(@PathVariable text: String): List<Suggestion> {
        return suggestionService.filterSuggestions(text)
    }

    // return a single json if profane words are found
    // gets the profane words too
    @GetMapping("/filter/single/{text:.+}")
    fun filterTextWithSingleSuggestion(@PathVariable text: String): Suggestion {
        val profanities = suggestionService.getProfaneWords(text)
        return Suggestion(
            id = UUID.randomUUID(),
            text = "Inappropriate language detected",
            profaneWordsFound = profanities
        )
    }

    @PostMapping
    fun addSuggestion(@RequestBody suggestion: Suggestion) = suggestionService.addSuggestion(suggestion)

    @DeleteMapping("/{id}")
    fun deleteSuggestion(@PathVariable id: Long) = suggestionService.deleteById(id)
}