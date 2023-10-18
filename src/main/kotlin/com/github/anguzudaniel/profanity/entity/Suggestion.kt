package com.github.anguzudaniel.profanity.entity

import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "suggestions")
data class Suggestion(
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "BINARY(16)")  // Storing UUID as binary
    var id: UUID?,

    @Column(length = 500)  // Adjust the length as needed
    var text: String,

    @Column(name = "contains_profanity")
    var containsProfanity: Boolean = false,

    @Column(name = "profanity_score")
    var profanityScore: Double = 0.0,

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "suggestion_id") // This is the foreign key column in the SuggestionResponse table
    var suggestionResponses: MutableList<SuggestionResponse> = mutableListOf(),

    @Column(name = "created_by")
    var createdBy: String? = null,

    @Column(name = "created_date")
    var createdDate: LocalDateTime? = LocalDateTime.now()
)

