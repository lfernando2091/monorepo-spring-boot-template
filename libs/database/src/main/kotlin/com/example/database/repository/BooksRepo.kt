package com.example.database.repository

import com.example.database.models.Status
import com.example.database.schemas.Book
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface BooksRepo: CoroutineCrudRepository<Book, ObjectId> {
    @Query("{ '_id': ?0, 'status': ?1}")
    suspend fun getById(id: ObjectId, status: Status = Status.ACTIVE): Book?
}