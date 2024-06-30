package com.example.database.services

import com.example.database.models.BookCreate
import com.example.database.repository.BooksRepo
import com.example.database.schemas.Book
import org.bson.types.ObjectId

interface BookService {
    suspend fun create(data: BookCreate): Book
    suspend fun del(id: ObjectId): ObjectId
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

    override suspend fun del(id: ObjectId): ObjectId {
        bookRepo.deleteById(id)
        return id
    }
}