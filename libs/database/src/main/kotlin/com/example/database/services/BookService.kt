package com.example.database.services

import com.example.database.models.BookCreate
import com.example.database.repository.BooksRepo
import com.example.database.schemas.Book

interface BookService {
    suspend fun create(data: BookCreate): Book
}

class BookServiceImpl (
    val bookRepo: BooksRepo
): BookService {
    override suspend fun create(data: BookCreate): Book =
        bookRepo
            .save(
                Book(
                    data.name
                )
            )
}