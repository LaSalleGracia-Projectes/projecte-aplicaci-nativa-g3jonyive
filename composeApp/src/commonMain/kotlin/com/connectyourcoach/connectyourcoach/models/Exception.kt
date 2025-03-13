package com.connectyourcoach.connectyourcoach.models

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