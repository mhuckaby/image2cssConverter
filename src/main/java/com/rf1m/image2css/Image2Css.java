/**
 * Image2Css converts image files into Base64 text-based CSS classes using
 * the URL-data field of a background image. It can be used to convert a single
 * file or several. For more information on command-line usage see file, "readme.txt".
 *
 * Distribution can be found at http://code.google.com/p/image2css/
 *
 * This product includes software developed by
 * The Apache Software Foundation (http://www.apache.org/).
 *
 * Please see files "NOTICE" and "LICENSE" for more information.
 *
 * @author Matthew D. Huckaby
 */
package com.rf1m.image2css;

import com.rf1m.image2css.cli.Parameters;
import com.rf1m.image2css.domain.SupportedImageType;
import com.rf1m.image2css.ioc.BeanType;
import com.rf1m.image2css.domain.CssClass;
import com.rf1m.image2css.ioc.ObjectFactory;
import com.rf1m.image2css.exception.Errors;
import com.rf1m.image2css.exception.Image2CssException;
import com.rf1m.image2css.exception.Image2CssValidationException;
import com.rf1m.image2css.out.Output;
import com.rf1m.image2css.out.ReportOutput;
import com.rf1m.image2css.util.bin.Base64Encoder;
import com.rf1m.image2css.util.file.ConversionFilenameFilter;
import com.rf1m.image2css.util.file.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import static com.rf1m.image2css.exception.Errors.*;
import static java.lang.String.format;

public class Image2Css {
    protected final Base64Encoder base64Encoder;

    protected final FileUtils fileUtils;

    protected final ObjectFactory objectFactory;

    protected final Output consoleOutput;
    protected final Output cssOutput;
    protected final Output htmlOutput;

    protected final ReportOutput reportOutput;

    protected final String cssClassTemplate;

    public Image2Css(
        final ObjectFactory objectFactory,
        final Base64Encoder base64Encoder,
        final FileUtils fileUtils,
        final Output consoleOutput,
        final Output cssOutput,
        final Output htmlOutput,
        final ReportOutput reportOutput,
        final String cssClassTemplate){

        this.objectFactory = objectFactory;
        this.base64Encoder = base64Encoder;
        this.fileUtils = fileUtils;
        this.consoleOutput = consoleOutput;
        this.cssOutput = cssOutput;
        this.htmlOutput = htmlOutput;
        this.reportOutput = reportOutput;
        this.cssClassTemplate = cssClassTemplate;
    }

	/**
	 * Begins conversion of image file(s) to CSS classes.
	 * @param parameters
	 * @throws Exception
	 */
	public void execute(final Parameters parameters) throws Image2CssException {
		this.validateParameters(parameters);

        final File imageFile = parameters.getImageFile();
        final Set<SupportedImageType> supportedImageTypes = parameters.getSupportedTypes();
        final File[] imageFiles = getImagesForConversion(imageFile, supportedImageTypes);
        final List<CssClass> cssClasses = generateCSSEntries(imageFiles);

        this.doOutput(parameters, cssClasses);

	}

    protected void doOutput(final Parameters parameters, final List<CssClass> cssClasses) throws Image2CssException {
        try{
            if(parameters.isOutputToConsoleDesired()) {
                consoleOutput.out(parameters, cssClasses);
            }

            if(parameters.isCssFileOutputDesired()) {
                cssOutput.out(parameters, cssClasses);
            }

            if(parameters.isHtmlFileOutputDesired()) {
                htmlOutput.out(parameters, cssClasses);
            }

            if(parameters.isOutputToConsoleDesired()) {
                reportOutput.generateReportOutput(parameters, cssClasses);
            }
        }catch(IOException e) {
            throw new Image2CssException(e, Errors.failureToOutput);
        }
    }

	/**
	 * Builds CSS class entries. CSS class name is the image-filename with all periods replaced by underscores.
	 * @return
	 * @throws IOException
	 */
	protected List<CssClass> generateCSSEntries(final File[] imageFiles) throws Image2CssException {
        try{
            final List<CssClass> cssEntries = this.objectFactory.instance(BeanType.arrayList);

            for(final File imageFile : imageFiles){
                final String cssName = imageFile.getName().replaceAll("\\.","_");
                final String imageFileExtension = fileUtils.getExtension(imageFile.getName());
                final byte[] bytes = fileUtils.getFileBytes(imageFile);
                final String base64Bytes = base64Encoder.base64EncodeBytes(bytes);

                final Dimension dimension = getImageDimension(bytes);
                final int height = (int)dimension.getHeight();
                final int width = (int)dimension.getWidth();
                final String cssEntry = format(cssClassTemplate, cssName, imageFileExtension, base64Bytes, width, height);
                final CssClass cssClass = this.objectFactory.instance(BeanType.cssClass, cssName, cssEntry);

                cssEntries.add(cssClass);
            }

            return cssEntries;
        }catch(IOException e) {
            throw new Image2CssException(e, Errors.failureToOutput);
        }
	}

    /**
     * Return the dimensions of an image represented by a byte array.
     * @param bytes
     * @return
     */
    protected Dimension getImageDimension(final byte[] bytes){
        final ImageIcon imageIcon = this.objectFactory.instance(BeanType.imageIcon, bytes);
        final Dimension dimension = this.objectFactory.instance(BeanType.dimension, imageIcon.getIconWidth(), imageIcon.getIconHeight());

        return dimension;
    }

	protected File[] getImagesForConversion(final File imageFile, final Set<SupportedImageType> supportedTypes) throws Image2CssException {
		File[] imagesForConversion;

		if(imageFile.isDirectory()){
            final Set<SupportedImageType> defaultSupportedImageTypes = this.objectFactory.instance(BeanType.supportedImageTypes);

            final Set<SupportedImageType> supportedTypesToFilterFor =
                supportedTypes.isEmpty() ? defaultSupportedImageTypes : supportedTypes;

            final ConversionFilenameFilter filter =
                this.objectFactory.instance(BeanType.conversionFilenameFilter, supportedTypesToFilterFor);

			imagesForConversion = imageFile.listFiles(filter);
		}else{
			imagesForConversion = this.objectFactory.instance(BeanType.fileArray, imageFile);
		}

		return imagesForConversion;
	}

    public void validateParameters(Parameters parameters) throws Image2CssException {
        if(null == parameters){
            throw new Image2CssValidationException(parametersObjectCannotBeNull) ;
        }else if(null == parameters.getImageFile()){
            throw new Image2CssValidationException(parametersObjectMustHaveValidImageInputFileOrDir);
        }else if(!parameters.getImageFile().exists()){
            throw new Image2CssValidationException(parametersObjectImageInputFileOrDirNotExists);
        }else if(!parameters.isOutputValid()){
            throw new Image2CssValidationException(parametersObjectOutputInvalid);
        }else if(parameters.isHtmlFileOutputDesired() && !parameters.isCssFileOutputDesired()) {
            throw new Image2CssValidationException(parameterHtmlIndexWithNoCssFile);
        }
    }

}