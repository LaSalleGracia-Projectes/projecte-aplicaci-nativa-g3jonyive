package com.connectyourcoach.connectyourcoach.models

import kotlinx.serialization.Serializable

@Serializable
enum class Exception {
    ModelNotFoundException,
    ModelAlreadyExistsException,
    ValidationException,
    UnauthorizedException,
    InternalServerErrorException,
    BadRequestException,
    PageNotFound,
    MethodNotAllowed,
    FirebaseException,
}