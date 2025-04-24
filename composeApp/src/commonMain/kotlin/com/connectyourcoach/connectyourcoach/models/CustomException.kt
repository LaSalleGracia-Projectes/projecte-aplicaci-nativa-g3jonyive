package com.connectyourcoach.connectyourcoach.models

import kotlinx.serialization.Serializable

@Serializable
enum class CustomException {
    ModelNotFoundException,
    ModelAlreadyExistsException,
    ValidationException,
    UnauthorizedException,
    InternalServerError,
    BadRequestException,
    PageNotFound,
    MethodNotAllowed,
    FirebaseException,
    FailedToConnectException,
}