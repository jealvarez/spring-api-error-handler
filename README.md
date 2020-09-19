# **Spring Api Error Handler**

Provides the configuration to catch any exception thrown transforming it in a custom json error response.

## **Author**

Jorge Alvarez <alvarez.jeap@gmail.com>

## **Requirements**

- Java 11+

## **How to use it?**

- Include dependency

  - Maven

    ```xml
    <dependency>
      <groupId>org.jealvarez</groupId>
      <artifactId>spring-api-error-handler</artifactId>
      <version>1.0</version>
    </dependency>
    ```

  - Gradle

    ```text
    implementation 'org.jealvarez:spring-api-error-handler:1.0'
    ```

- Create custom error handlers

```java
import org.jealvarez.api.error.handling.model.enums.ErrorLocation;
import org.jealvarez.api.error.handling.web.response.controller.ErrorHandlerRestControllerAdviceDefault;
import org.jealvarez.api.error.handling.web.response.factory.ApiResponseFactory;
import org.jealvarez.api.error.handling.web.response.factory.DetailErrorResponseFactory;
import org.jealvarez.api.error.handling.web.response.model.ApiResponse;
import org.jealvarez.api.error.handling.web.response.model.ErrorResponseDetail;
import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;
import io.vavr.Tuple2;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.function.Function;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Function;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;
import static java.util.List.of;

@RestControllerAdvice
public class ErrorHandlerRestControllerAdvice extends ErrorHandlerRestControllerAdviceDefault {

    public ErrorHandlerRestControllerAdvice(final ApiResponseFactory apiResponseFactory, final DetailErrorResponseFactory detailErrorResponseFactory) {
        super(apiResponseFactory, detailErrorResponseFactory);
    }

    @Override
    protected List<Tuple2<Class<? extends Throwable>, Function<Throwable, ApiResponse>>> configureCustomExceptionHandlers() {
        return List.of(
                new Tuple2<>(HttpMessageNotReadableException.class, API.Function(this::handleHttpMessageNotReadableException))
        );
    }

    private ApiResponse handleHttpMessageNotReadableException(final Throwable throwable) {
        final HttpMessageNotReadableException httpMessageNotReadableException = (HttpMessageNotReadableException) throwable;
        final ErrorResponseDetail errorResponseDetail = Match(httpMessageNotReadableException.getCause()).of(
                Case($(instanceOf(InvalidTypeIdException.class)), buildErrorResponseDetail("payment_method",
                                                                                           PaymentMethod.UNSUPPORTED_PAYMENT_METHOD,
                                                                                           ErrorLocation.BODY)));

        return apiResponseFactory.buildErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, List.of(errorResponseDetail));
    }

}
```

- Result

```json
{
  "name": "UNPROCESSABLE_ENTITY",
  "details": [
    {
      "value": "1234567890123456a",
      "issue": "INVALID_PARAMETER_VALUE",
      "description": "The value of a field is invalid.",
      "field": "paymentMethod.accountNumber",
      "location": "body"
    },
    {
      "value": "U",
      "issue": "INVALID_STRING_LENGTH",
      "description": "The value of a field is either too short or too long.",
      "field": "paymentMethod.issuingCountryIsoCode",
      "location": "body"
    }
  ],
  "message": "The requested action could not be performed, semantically incorrect, or failed business validation."
}
```
