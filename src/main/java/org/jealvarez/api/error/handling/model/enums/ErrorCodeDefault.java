package org.jealvarez.api.error.handling.model.enums;

import org.jealvarez.api.error.handling.model.Error;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCodeDefault implements Error {

    INVALID_REQUEST("Request is not well-formed, syntactically incorrect, or violates schema."),
    AUTHENTICATION_FAILURE("Authentication failed due to missing Authorization header, or invalid authentication credentials."),
    NOT_AUTHORIZED("Authorization failed due to insufficient permissions."),
    RESOURCE_NOT_FOUND("The specified resource does not exist."),
    METHOD_NOT_SUPPORTED("Method The server does not implement the requested HTTP method."),
    MEDIA_TYPE_NOT_ACCEPTABLE("The server does not implement the media type that would be acceptable to the client."),
    RESOURCE_CONFLICT("The server has detected a conflict while processing this request."),
    UNSUPPORTED_MEDIA_TYPE("Unsupported The server does not support the request payload’s media type."),
    PRECONDITION_FAILED("Update failed. Resource has changed on the server."),
    PAYLOAD_TOO_LARGE("Payload The request entity is larger than the size limit defined by the server."),
    UNPROCESSABLE_ENTITY("The requested action could not be performed, semantically incorrect, or failed business validation."),
    MISSING_PRECONDITION_HEADER("This request must be conditional and requires a If-Match header."),
    RATE_LIMIT_REACHED("Too Too many requests. Blocked due to rate limiting."),
    INTERNAL_SERVER_ERROR("Internal An internal server error has occurred."),
    SERVICE_UNAVAILABLE("Service Unavailable.");

    private final String message;

}
