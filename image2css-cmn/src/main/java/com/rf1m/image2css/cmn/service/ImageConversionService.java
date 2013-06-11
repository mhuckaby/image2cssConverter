package com.rf1m.image2css.cmn.service;

import com.rf1m.image2css.cmn.domain.CssClass;

import java.io.File;

public interface ImageConversionService {

    CssClass convert(final File imageFile);

}
