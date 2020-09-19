package org.jealvarez.api.error.handling.web.response.factory;

import org.jealvarez.api.error.handling.model.Error;
import org.jealvarez.api.error.handling.model.enums.ErrorCodeDefault;
import org.jealvarez.api.error.handling.web.response.model.ApiResponse;
import org.jealvarez.api.error.handling.web.response.model.ErrorResponse;
import org.jealvarez.api.error.handling.web.response.model.ErrorResponseDetail;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class ErrorResponseFactory {

    private final Map<HttpStatus, Error> errorCodeToHttpStatusMap;

    public ErrorResponseFactory() {
        final Map<HttpStatus, Error> errorCodeHttpStatusMap = new EnumMap<>(HttpStatus.class);
        errorCodeHttpStatusMap.put(HttpStatus.BAD_REQUEST, ErrorCodeDefault.INVALID_REQUEST);
        errorCodeHttpStatusMap.put(HttpStatus.UNAUTHORIZED, ErrorCodeDefault.AUTHENTICATION_FAILURE);
        errorCodeHttpStatusMap.put(HttpStatus.FORBIDDEN, ErrorCodeDefault.NOT_AUTHORIZED);
        errorCodeHttpStatusMap.put(HttpStatus.NOT_FOUND, ErrorCodeDefault.RESOURCE_NOT_FOUND);
        errorCodeHttpStatusMap.put(HttpStatus.METHOD_NOT_ALLOWED, ErrorCodeDefault.METHOD_NOT_SUPPORTED);
        errorCodeHttpStatusMap.put(HttpStatus.NOT_ACCEPTABLE, ErrorCodeDefault.MEDIA_TYPE_NOT_ACCEPTABLE);
        errorCodeHttpStatusMap.put(HttpStatus.CONFLICT, ErrorCodeDefault.RESOURCE_CONFLICT);
        errorCodeHttpStatusMap.put(HttpStatus.PRECONDITION_FAILED, ErrorCodeDefault.PRECONDITION_FAILED);
        errorCodeHttpStatusMap.put(HttpStatus.PAYLOAD_TOO_LARGE, ErrorCodeDefault.PAYLOAD_TOO_LARGE);
        errorCodeHttpStatusMap.put(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ErrorCodeDefault.UNSUPPORTED_MEDIA_TYPE);
        errorCodeHttpStatusMap.put(HttpStatus.UNPROCESSABLE_ENTITY, ErrorCodeDefault.UNPROCESSABLE_ENTITY);
        errorCodeHttpStatusMap.put(HttpStatus.PRECONDITION_REQUIRED, ErrorCodeDefault.MISSING_PRECONDITION_HEADER);
        errorCodeHttpStatusMap.put(HttpStatus.TOO_MANY_REQUESTS, ErrorCodeDefault.RATE_LIMIT_REACHED);
        errorCodeHttpStatusMap.put(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCodeDefault.INTERNAL_SERVER_ERROR);
        errorCodeHttpStatusMap.put(HttpStatus.SERVICE_UNAVAILABLE, ErrorCodeDefault.SERVICE_UNAVAILABLE);

        this.errorCodeToHttpStatusMap = Map.copyOf(errorCodeHttpStatusMap);
    }

    ApiResponse buildErrorResponse(final HttpStatus httpStatus, final List<ErrorResponseDetail> errorResponseDetails) {
        final ErrorResponse errorResponse = new ErrorResponse(errorCodeToHttpStatusMap.getOrDefault(httpStatus, ErrorCodeDefault.INTERNAL_SERVER_ERROR), errorResponseDetails);

        return new ApiResponse(errorResponse, httpStatus);
    }

}
