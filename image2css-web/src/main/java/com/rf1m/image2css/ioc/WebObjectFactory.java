package com.rf1m.image2css.ioc;

import com.rf1m.image2css.web.domain.ErrorResponse;

public class WebObjectFactory {

    public ErrorResponse newErrorResponse(final String message) {
        return new ErrorResponse(message);
    }
}
