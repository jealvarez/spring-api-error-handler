package org.jealvarez.api.error.handling.web.response.controller;

import io.vavr.Tuple2;
import org.jealvarez.api.error.handling.model.Error;
import org.jealvarez.api.error.handling.model.enums.ErrorLocation;
import org.jealvarez.api.error.handling.web.response.factory.ApiResponseFactory;
import org.jealvarez.api.error.handling.web.response.factory.DetailErrorResponseFactory;
import org.jealvarez.api.error.handling.web.response.model.ApiResponse;
import org.jealvarez.api.error.handling.web.response.model.ErrorResponseDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.vavr.API.Function;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@RestControllerAdvice
public class ErrorHandlerRestControllerAdviceDefault {

    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorHandlerRestControllerAdviceDefault.class);

    private final List<Tuple2<Class<? extends Throwable>, Function<Throwable, ApiResponse>>> DEFAULT_EXCEPTION_HANDLERS = List.of(
            new Tuple2<>(MethodArgumentNotValidException.class, Function(this::handleMethodArgumentNotValidException))
    );

    protected final ApiResponseFactory apiResponseFactory;
    protected final DetailErrorResponseFactory detailErrorResponseFactory;

    public ErrorHandlerRestControllerAdviceDefault(final ApiResponseFactory apiResponseFactory,
                                                   final DetailErrorResponseFactory detailErrorResponseFactory) {
        this.apiResponseFactory = apiResponseFactory;
        this.detailErrorResponseFactory = detailErrorResponseFactory;
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse handleException(final Exception exception) {
        LOGGER.error(exception.getMessage(), exception);

        final var customExceptionHandlers = configureCustomExceptionHandlers();

        Assert.isTrue(customExceptionHandlers != null, "custom exception handlers must not be null");

        final var exceptionHandlers = Stream.of(customExceptionHandlers, DEFAULT_EXCEPTION_HANDLERS)
                                            .flatMap(Collection::stream)
                                            .collect(Collectors.toList());

        final Optional<Tuple2<Class<? extends Throwable>, Function<Throwable, ApiResponse>>> errorHandlerResult = exceptionHandlers.stream()
                                                                                                                                   .filter(tuple -> Objects.equals(tuple._1(), exception.getClass()))
                                                                                                                                   .findFirst();

        if (errorHandlerResult.isPresent()) {
            return errorHandlerResult.get()._2().apply(exception);
        }

        return apiResponseFactory.buildErrorResponse(INTERNAL_SERVER_ERROR);
    }

    /*
     * Override this method if you want to add custom exception handlers
     */
    protected List<Tuple2<Class<? extends Throwable>, Function<Throwable, ApiResponse>>> configureCustomExceptionHandlers() {
        return emptyList();
    }

    private ApiResponse handleMethodArgumentNotValidException(final Throwable throwable) {
        final MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) throwable;
        final List<ErrorResponseDetail> errorResponseDetails = methodArgumentNotValidException.getBindingResult()
                                                                                              .getFieldErrors()
                                                                                              .stream()
                                                                                              .map(error -> buildErrorResponseDetail(error.getField(),
                                                                                                                                     error.getRejectedValue(),
                                                                                                                                     detailErrorResponseFactory.getDetailedErrorCode(error.getCode()),
                                                                                                                                     ErrorLocation.BODY))
                                                                                              .collect(toList());

        return apiResponseFactory.buildErrorResponse(UNPROCESSABLE_ENTITY, errorResponseDetails);
    }

    protected ErrorResponseDetail buildErrorResponseDetail(final String field, final Object value, final Error error, final ErrorLocation errorLocation) {
        return new ErrorResponseDetail(field, value, error, errorLocation);
    }

    protected ErrorResponseDetail buildErrorResponseDetail(final String field, final Error error, final ErrorLocation errorLocation) {
        return buildErrorResponseDetail(field, null, error, errorLocation);
    }

}
