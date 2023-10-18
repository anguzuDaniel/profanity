package com.github.anguzudaniel.profanity.services

import com.github.anguzudaniel.profanity.entity.Suggestion
import com.github.anguzudaniel.profanity.profaneWords
import com.github.anguzudaniel.profanity.repositories.SuggestionRepository
import org.springframework.stereotype.Service
import java.net.URLDecoder
import java.util.*

@Service
class SuggestionService(private val db: SuggestionRepository) {
    fun getSuggestions(): List<Suggestion> = db.findAll().toList()

    // filters through the text and sees checks for profanity
    // if there's profanity a List of suggestions is sent else and empty list
    fun filterSuggestions(encodedText: String): List<Suggestion> {
        // The 'text' may contain something like "What%20the%20fuck%20are%20you%20saying"
        // decode and then filter the text.
        val decodedText = URLDecoder.decode(encodedText, "UTF-8")

        // splits the by space
        val words = decodedText.split(" ")

        val suggestions = mutableListOf<Suggestion>()
        for (word in words) {
            if (profaneWords.contains(word)) {
                // if a profane word is found, create a new suggestion
                val suggestion = Suggestion(
                    id = UUID.randomUUID(),
                    "Inappropriate language detected: $word",
                )
                suggestions.add(suggestion)
            }
        }

        return suggestions
    }

    fun getProfaneWords(encodedText: String): List<String> {
        // The 'text' may contain something like "What%20the%20fuck%20are%20you%20saying"
        // decode and then filter the text.
        val decodedText = URLDecoder.decode(encodedText, "UTF-8")

        // splits the by space
        val words = decodedText.split(" ")

        val suggestions = mutableListOf<String>()
        for (word in words) {
            if (profaneWords.contains(word)) {
                suggestions.add(word)
            }
        }

        return suggestions
    }
}