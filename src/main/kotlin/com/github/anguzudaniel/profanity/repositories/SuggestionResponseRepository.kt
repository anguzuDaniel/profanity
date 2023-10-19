package com.github.anguzudaniel.profanity.repositories

import com.github.anguzudaniel.profanity.entity.SuggestionResponse
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface SuggestionResponseRepository : CrudRepository<SuggestionResponse, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM responses r ORDER BY r.id DESC LIMIT :num")
    fun limitSuggestionResponses(@Param("num") num: Int): List<SuggestionResponse>
}