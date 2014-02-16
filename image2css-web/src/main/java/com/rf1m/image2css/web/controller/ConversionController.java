package com.rf1m.image2css.web.controller;

import com.rf1m.image2css.domain.CssClass;
import com.rf1m.image2css.exception.Image2CssException;
import com.rf1m.image2css.exception.Image2CssValidationException;
import com.rf1m.image2css.cmn.service.ImageConversionService;
import com.rf1m.image2css.domain.ErrorResponse;
import com.rf1m.image2css.ioc.WebObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@Controller
public class ConversionController {
    protected static final Logger logger = LoggerFactory.getLogger(ConversionController.class);

    @Autowired
    ImageConversionService imageConversionService;

    @Autowired
    WebObjectFactory webObjectFactory;

    @RequestMapping(value = "/convert", produces = {APPLICATION_JSON_VALUE})
    @ResponseBody
    public CssClass convertUrl(@RequestParam final String url) {
        logger.info("convertUrl=" + url);
        return this.imageConversionService.convertUrlAsString(url);
    }

    @ExceptionHandler(Image2CssValidationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleBadRequest(final Image2CssValidationException image2CssValidationException) {
        return this.webObjectFactory.newErrorResponse(image2CssValidationException.getMessage());
    }

    @ExceptionHandler(value = {Image2CssException.class, Exception.class})
    @ResponseStatus(value = INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleInternalServerError(final Image2CssException image2cssException) {
        return this.webObjectFactory.newErrorResponse(image2cssException.getMessage());
    }


}
