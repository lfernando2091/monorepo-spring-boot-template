package com.example.database.schemas

import com.example.database.utils.BaseSchema
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document("books")
data class Book(
    @Indexed(unique = true, name = "idx_books_name")
    @Field("name")
    val name: String,
): BaseSchema()