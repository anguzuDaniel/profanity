package com.github.anguzudaniel.profanity.repositories

import com.github.anguzudaniel.profanity.entity.Suggestion
import com.github.anguzudaniel.profanity.entity.SuggestionResponse
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SuggestionResponseRepository : CrudRepository<SuggestionResponse, Long>