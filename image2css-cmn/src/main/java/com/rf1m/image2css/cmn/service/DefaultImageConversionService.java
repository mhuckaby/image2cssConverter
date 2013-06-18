package com.rf1m.image2css.cmn.service;

import com.rf1m.image2css.cmn.domain.CssClass;
import com.rf1m.image2css.cmn.ioc.CommonObjectType;
import com.rf1m.image2css.cmn.ioc.CommonObjectFactory;
import com.rf1m.image2css.cmn.util.bin.Base64Encoder;
import com.rf1m.image2css.cmn.util.file.FileUtils;
import org.apache.commons.lang3.tuple.Pair;

import javax.swing.*;
import java.io.File;

import static java.lang.String.format;

public class DefaultImageConversionService implements ImageConversionService {

    protected final FileUtils fileUtils;
    protected final Base64Encoder base64Encoder;
    protected final CommonObjectFactory commonObjectFactory;
    protected final String cssClassTemplate;

    public DefaultImageConversionService(FileUtils fileUtils, Base64Encoder base64Encoder, CommonObjectFactory commonObjectFactory, String cssClassTemplate) {
        this.fileUtils = fileUtils;
        this.base64Encoder = base64Encoder;
        this.commonObjectFactory = commonObjectFactory;
        this.cssClassTemplate = cssClassTemplate;
    }

    public CssClass convert(final File imageFile) {
        // TODO Validate parameter
        final String imageFilename = imageFile.getName();
        final String cssClassName = this.determineCssClassName(imageFilename);
        final String fileExtension = this.fileUtils.getExtension(imageFilename);
        final byte[] bytes = this.fileUtils.getFileBytes(imageFile);
        final String base64Bytes = this.base64Encoder.base64EncodeBytes(bytes);
        final Pair<Integer, Integer> dimension = this.getImageDimension(bytes);
        final String cssEntry = this.determineCssEntry(cssClassName, fileExtension, base64Bytes, dimension);
        final CssClass cssClass = this.commonObjectFactory.getInstance(CommonObjectType.cssClass, cssClassName, cssEntry);

        return cssClass;
    }

    protected String determineCssEntry(final String cssClassName, final String fileExtension, final String base64Bytes, final Pair<Integer, Integer> dimension) {
        return format(cssClassTemplate, cssClassName, fileExtension, base64Bytes, dimension.getLeft(), dimension.getRight());
    }

    protected String determineCssClassName(final String fileName) {
        return fileName.replaceAll("\\.","_");
    }

    /**
     * Return the dimension of an image represented by a byte array.
     * @param bytes
     * @return Pair with left as width and right as height.
     */
    protected Pair<Integer, Integer> getImageDimension(final byte[] bytes){
        final ImageIcon imageIcon = this.commonObjectFactory.getInstance(CommonObjectType.imageIcon, bytes);
        final Pair<Integer, Integer> result =
            this.commonObjectFactory.getInstance(CommonObjectType.pair, imageIcon.getIconWidth(), imageIcon.getIconHeight());

        return result;
    }
}
