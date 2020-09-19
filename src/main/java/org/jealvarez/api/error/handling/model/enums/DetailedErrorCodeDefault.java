package org.jealvarez.api.error.handling.model.enums;

import org.jealvarez.api.error.handling.model.Error;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DetailedErrorCodeDefault implements Error {

    MISSING_REQUIRED_PARAMETER("A required field or parameter is missing."),
    MALFORMED_REQUEST_JSON("The request JSON is not well formed."),
    INVALID_STRING_MIN_LENGTH("The value of a field is too short."),
    INVALID_STRING_MAX_LENGTH("The value of a field is too long."),
    INVALID_STRING_LENGTH("The value of a field is either too short or too long."),
    INVALID_PARAMETER_SYNTAX("The value of a field does not conform to the expected format."),
    INVALID_INTEGER_MIN_VALUE("The integer value of a field is too small."),
    INVALID_INTEGER_MAX_VALUE("The integer value of a field is too large."),
    INVALID_PARAMETER_VALUE("The value of a field is invalid."),
    INVALID_ARRAY_MIN_ITEMS("The number of items in an array parameter is too small."),
    INVALID_ARRAY_MAX_ITEMS("The number of items in an array parameter is too large."),
    INVALID_ARRAY_LENGTH("The number of items in an array parameter is too small or too large."),
    INVALID_PATCH_PATH("The value of path in a HTTP PATCH request item is invalid."),
    READ_ONLY("The field in the request body is read only so it can’t be modified."),
    CANNOT_REUSE_IDEMPOTENCE_KEY("The idempotence key cannot be reused for a different request payload."),
    EXCEEDS_MAXIMUM_NUMBER_OF_OPERATIONS("The request body exceeds maximum allowed operations for a batch.");

    private final String message;

}
