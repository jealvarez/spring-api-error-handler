package org.jealvarez.api.error.handling.web.response.factory;

import org.jealvarez.api.error.handling.web.response.model.ApiResponse;
import org.jealvarez.api.error.handling.web.response.model.ErrorResponseDetail;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApiResponseFactory {

    private final ErrorResponseFactory errorResponseFactory;

    public ApiResponseFactory(final ErrorResponseFactory errorResponseFactory) {
        this.errorResponseFactory = errorResponseFactory;
    }

    public <T> ApiResponse buildResponse(final HttpStatus httpStatus, final T body) {
        return new ApiResponse(body, httpStatus);
    }

    public ApiResponse buildResponse(final HttpStatus httpStatus) {
        return buildResponse(httpStatus, null);
    }

    public ApiResponse buildErrorResponse(final HttpStatus httpStatus, final List<ErrorResponseDetail> errorResponseDetails) {
        return errorResponseFactory.buildErrorResponse(httpStatus, errorResponseDetails);
    }

    public ApiResponse buildErrorResponse(final HttpStatus httpStatus) {
        return buildErrorResponse(httpStatus, null);
    }

}
