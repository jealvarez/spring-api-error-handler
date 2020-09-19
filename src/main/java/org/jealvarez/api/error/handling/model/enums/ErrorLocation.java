package org.jealvarez.api.error.handling.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorLocation {

    BODY("body"),
    QUERY("query"),
    PATH("path");

    private final String value;

}
