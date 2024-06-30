package com.example.app1.exception.models

import com.example.app1.exception.constants.Constants.BAD_REQUEST_CAUSE_MSG
import com.example.app1.exception.constants.Constants.BAD_REQUEST_ERROR_CODE
import com.example.app1.exception.constants.Constants.BAD_REQUEST_MSG
import com.example.app1.exception.constants.Constants.FORBIDDEN_ERROR_CODE
import com.example.app1.exception.constants.Constants.FORBIDDEN_REQUEST_CAUSE_MSG
import com.example.app1.exception.constants.Constants.FORBIDDEN_REQUEST_MSG
import com.example.app1.exception.constants.Constants.GENERIC_ERROR_CODE
import com.example.app1.exception.constants.Constants.INTERNAL_SERVER_ERROR_CAUSE_MSG
import com.example.app1.exception.constants.Constants.INTERNAL_SERVER_ERROR_MSG
import com.example.app1.exception.constants.Constants.NOT_FOUND_CAUSE_MSG
import com.example.app1.exception.constants.Constants.NOT_FOUND_ERROR_CODE
import com.example.app1.exception.constants.Constants.NOT_FOUND_MSG
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

abstract class BaseException(
    message: String = INTERNAL_SERVER_ERROR_MSG,
    cause: String = INTERNAL_SERVER_ERROR_CAUSE_MSG,
    status: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
    val errorCode: String = GENERIC_ERROR_CODE
): ResponseStatusException(status, message, Throwable(cause))

class NotFoundException(
    message: String = NOT_FOUND_MSG,
    cause: String = NOT_FOUND_CAUSE_MSG,
    status: HttpStatus = HttpStatus.NOT_FOUND,
    errorCode: String = NOT_FOUND_ERROR_CODE
): BaseException(message, cause, status, errorCode)

class BadRequestException(
    message: String = BAD_REQUEST_MSG,
    cause: String = BAD_REQUEST_CAUSE_MSG,
    status: HttpStatus = HttpStatus.BAD_REQUEST,
    errorCode: String = BAD_REQUEST_ERROR_CODE
): BaseException(message, cause, status, errorCode)

class ForbiddenRequestException(
    message: String = FORBIDDEN_REQUEST_MSG,
    cause: String = FORBIDDEN_REQUEST_CAUSE_MSG,
    status: HttpStatus = HttpStatus.FORBIDDEN,
    errorCode: String = FORBIDDEN_ERROR_CODE
): BaseException(message, cause, status, errorCode)