package com.github.anguzudaniel.profanity.entity

import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "responses")
data class SuggestionResponse(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    @Column(length = 500, name = "response_text")  // Adjust the length as needed
    var responseText: String,

    @Column(name = "created_by")
    var createdBy: String? = null,

    @Column(name = "created_date")
    var createdDate: LocalDateTime? = LocalDateTime.now(),

    @ManyToOne
    @JoinColumn(name = "suggestion_id")  // This is the foreign key column
    var suggestion: Suggestion?  // Many-to-One relationship to Suggestion entity
)

