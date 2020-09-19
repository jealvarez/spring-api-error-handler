package org.jealvarez.api.error.handling.web.response.factory;

import org.jealvarez.api.error.handling.model.Error;
import org.jealvarez.api.error.handling.model.enums.DetailedErrorCodeDefault;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DetailErrorResponseFactory {

    private final Map<String, Error> detailedErrorCodeMap;

    public DetailErrorResponseFactory() {
        final Map<String, Error> detailedErrorCodeMap = new HashMap<>();
        detailedErrorCodeMap.put("NotNull", DetailedErrorCodeDefault.MISSING_REQUIRED_PARAMETER);
        detailedErrorCodeMap.put("Max", DetailedErrorCodeDefault.INVALID_STRING_MIN_LENGTH);
        detailedErrorCodeMap.put("Min", DetailedErrorCodeDefault.INVALID_STRING_MAX_LENGTH);
        detailedErrorCodeMap.put("Size", DetailedErrorCodeDefault.INVALID_STRING_LENGTH);

        this.detailedErrorCodeMap = Map.copyOf(detailedErrorCodeMap);
    }

    public Error getDetailedErrorCode(final String errorKey) {
        return detailedErrorCodeMap.getOrDefault(errorKey, DetailedErrorCodeDefault.INVALID_PARAMETER_VALUE);
    }

}
