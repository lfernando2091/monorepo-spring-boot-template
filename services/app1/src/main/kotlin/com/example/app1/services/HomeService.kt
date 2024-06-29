package com.example.app1.services

import com.example.app1.models.HomeDto
import com.example.database.models.BookCreate
import com.example.database.services.BookService
import org.springframework.stereotype.Service

interface HomeService {
    suspend fun get(): HomeDto
}

@Service
class HomeServiceImpl(
    val bookService: BookService
): HomeService {
    override suspend fun get(): HomeDto {
        bookService.create(BookCreate(
            "Hola mundo"
        ))
        return HomeDto("Hello...")
    }
}