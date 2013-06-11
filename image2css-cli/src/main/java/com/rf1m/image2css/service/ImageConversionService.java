package com.rf1m.image2css.service;

import com.rf1m.image2css.domain.CssClass;

import java.io.File;

public interface ImageConversionService {

    CssClass convert(final File imageFile);

}
