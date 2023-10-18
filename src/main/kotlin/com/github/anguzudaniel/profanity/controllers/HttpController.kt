package com.github.anguzudaniel.profanity.controllers

import com.github.anguzudaniel.profanity.entity.Suggestion
import com.github.anguzudaniel.profanity.services.SuggestionService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/v1/profanity")
class HttpController(private val service: SuggestionService) {

    @GetMapping("/")
    fun getSuggestions(): List<Suggestion> = service.getSuggestions()

    // returns multiple json objects of profane words are found
    @GetMapping("/filter/multiple/{text:.+}")
    fun filterTextWithMultipleSuggestions(@PathVariable text: String): List<Suggestion> {
        return service.filterSuggestions(text)
    }

    // return a single json if profane words are found
    // gets the profane words too
    @GetMapping("/filter/single/{text:.+}")
    fun filterTextWithSingleSuggestion(@PathVariable text: String): Suggestion {
        val profanities = service.getProfaneWords(text)
        return Suggestion(
            id = UUID.randomUUID(),
            text = "Inappropriate language detected",
            profaneWordsFound = profanities
        )
    }
}