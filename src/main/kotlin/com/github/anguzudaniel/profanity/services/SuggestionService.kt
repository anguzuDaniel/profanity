package com.github.anguzudaniel.profanity.services

import com.github.anguzudaniel.profanity.entity.Suggestion
import com.github.anguzudaniel.profanity.repositories.SuggestionRepository
import org.springframework.stereotype.Service

@Service
class SuggestionService(private val db: SuggestionRepository) {
    fun getSuggestions(): List<Suggestion> = db.findAll().toList()
}