package com.example.app1.services

import com.example.app1.exception.models.BadRequestException
import com.example.app1.models.HomeDto
import com.example.app1.models.HomeReq
import com.example.app1.models.HomeRes
import com.example.database.models.BookCreate
import com.example.database.services.BookService
import org.springframework.stereotype.Service

interface HomeService {
    suspend fun get(name: String): HomeDto
    suspend fun post(input: HomeReq): HomeRes
    suspend fun getWithError(): HomeDto
}

@Service
class HomeServiceImpl(
    val bookService: BookService
): HomeService {
    override suspend fun get(name: String): HomeDto {
        return HomeDto(name)
    }

    override suspend fun post(input: HomeReq): HomeRes {
        val saved = bookService.create(BookCreate(
            input.messageInput
        ))
        return HomeRes(
            saved.id,
            saved.name
        )
    }

    override suspend fun getWithError(): HomeDto =
        throw BadRequestException("Exception generated.")
}