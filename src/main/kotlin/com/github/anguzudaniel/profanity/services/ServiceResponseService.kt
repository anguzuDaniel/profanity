package com.github.anguzudaniel.profanity.services

import com.github.anguzudaniel.profanity.entity.SuggestionResponse
import com.github.anguzudaniel.profanity.repositories.SuggestionResponseRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class SuggestionResponseService(private val db: SuggestionResponseRepository) {
    fun getNumberOfSuggestionAvailable() = db.count()

    fun getSuggestionResponse(): List<SuggestionResponse> = db.findAll().toList()

    fun addSuggestions(suggestionResponse: SuggestionResponse) = db.save(suggestionResponse)

    fun deleteSuggestionResponse(id: Long) = db.deleteById(id)
    fun getLimitedNumberOfSuggestions(num: Int): List<SuggestionResponse> = db.limitSuggestionResponses(num)

    // creates a suggestion mechanism based on filtered text provided
    // TODO: create and better recommendation system
    fun getAppropriateResponses(text: String): MutableList<SuggestionResponse> {
        val words = decodeString(text.lowercase(Locale.getDefault()))
        val responses = mutableListOf<SuggestionResponse>()

        if (words.contains("what")) {
            return getSuggestionResponse().toMutableList()
        }

        // Common polite responses
        responses.add(SuggestionResponse(id = 1, responseText = "Thank you.", suggestion = null))
        responses.add(SuggestionResponse(id = 2, responseText = "You're welcome!", suggestion = null))

        // Context-aware responses
        if (words.contains("help")) {
            responses.add(SuggestionResponse(id = 3, responseText = "How can I assist you?", suggestion = null))
            responses.add(SuggestionResponse(id = 4, responseText = "I'm here to help. What do you need?", suggestion = null))
        }

        // Feedback-related responses
        if (words.contains("feedback")) {
            responses.add(SuggestionResponse(id = 5, responseText = "Thank you for your feedback.", suggestion = null))
            responses.add(SuggestionResponse(id = 6, responseText = "We appreciate your input.", suggestion = null))
        }

        // Add more specific responses based on the context

        return responses
    }
}