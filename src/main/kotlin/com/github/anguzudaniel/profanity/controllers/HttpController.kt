package com.github.anguzudaniel.profanity.controllers

import com.github.anguzudaniel.profanity.entity.Suggestion
import com.github.anguzudaniel.profanity.services.SuggestionService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/profanity")
class HttpController(val service: SuggestionService) {

    @GetMapping("/")
    fun getSuggestions(): List<Suggestion> = service.getSuggestions()
}