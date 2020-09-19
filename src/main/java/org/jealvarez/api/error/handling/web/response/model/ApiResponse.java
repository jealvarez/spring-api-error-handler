package org.jealvarez.api.error.handling.web.response.model;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponse extends ResponseEntity {

    public ApiResponse(final Object body, final HttpStatus httpStatus) {
        super(body, httpStatus);
    }

}
