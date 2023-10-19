package com.github.anguzudaniel.profanity.services

import com.github.anguzudaniel.profanity.entity.SuggestionResponse
import com.github.anguzudaniel.profanity.repositories.SuggestionResponseRepository
import org.springframework.stereotype.Service

@Service
class SuggestionResponseService(private val db: SuggestionResponseRepository) {
    fun getSuggestionResponse() = db.findAll().toList()

    fun addSuggestions(suggestionResponse: SuggestionResponse) = db.save(suggestionResponse)

    fun deleteSuggestionResponse(id: Long) = db.deleteById(id)
}