package org.jealvarez.api.error.handling.web.response.model;

import org.jealvarez.api.error.handling.model.enums.ErrorLocation;
import org.jealvarez.api.error.handling.model.Error;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@Builder
@Getter
@AllArgsConstructor
@JsonInclude(NON_EMPTY)
@JsonPropertyOrder({"value", "issue", "description"})
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ErrorResponseDetail {

    private final String field;
    private final Object value;
    private final String issue;
    private final String description;
    private final String location;

    @JsonIgnore
    private final Error error;

    public ErrorResponseDetail(final String field,
                               final Object value,
                               final Error error,
                               final ErrorLocation errorLocation) {
        this.field = field;
        this.value = value;
        this.issue = error.toString();
        this.description = error.getMessage();
        this.error = error;
        this.location = errorLocation != null ? errorLocation.getValue() : null;
    }

    public ErrorResponseDetail(final String field,
                               final Error error,
                               final ErrorLocation errorLocation) {
        this(field, null, error, errorLocation);
    }

}
