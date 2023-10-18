package com.github.anguzudaniel.profanity.repositories

import com.github.anguzudaniel.profanity.entity.Suggestion
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SuggestionRepository : CrudRepository<Suggestion, Long>