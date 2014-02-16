package com.rf1m.image2css.web.controller;

import com.rf1m.image2css.cmn.domain.CssClass;
import com.rf1m.image2css.cmn.service.ImageConversionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@Controller
public class ConversionController {
    protected static final Logger logger = LoggerFactory.getLogger(ConversionController.class);


    @Autowired
    ImageConversionService imageConversionService;

    @RequestMapping(value = "/convert", produces = {APPLICATION_JSON_VALUE})
    @ResponseBody
    public CssClass convertUrl(@RequestParam final String url) {
        logger.info("convertUrl=" + url);
        return this.imageConversionService.convertUrlAsString(url);
    }
}
