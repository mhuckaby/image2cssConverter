package com.rf1m.web.image2css;

import com.rf1m.util.bin.Base64Encoder;
import com.rf1m.util.file.FileUtils;
import com.rf1m.util.file.Image2CssConversionFilenameFilter;
import com.rf1m.web.image2css.cli.Parameters;
import com.rf1m.web.image2css.cli.SupportedImageTypes;
import com.rf1m.web.image2css.domain.CSSClass;
import com.rf1m.web.image2css.out.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.rf1m.util.PropertiesUtils.getProperty;
import static com.rf1m.web.image2css.ContentTemplates.TEMPLATE.CSS_CLASS;
import static java.lang.String.format;

/**
 * Image2CSS converts image files into Base64 text-based CSS classes using 
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
public class Image2CSS {
    protected Base64Encoder base64Encoder;
    protected FileUtils fileUtils;

    protected Output consoleOutput;
    protected Output cssOutput;
    protected Output htmlOutput;

    protected ReportOutput reportOutput;

    protected ArrayListFactory arrayListFactory;
    protected CSSClassFactory cssClassFactory;
    protected DimensionFactory dimensionFactory;
    protected ImageIconFactory imageIconFactory;

    class ArrayListFactory{
        public ArrayList newArrayList(){
            return new ArrayList();
        }
    }

    class CSSClassFactory{
        public CSSClass newCSSClass(String name, String body){
            return new CSSClass(name, body);
        }
    }

    class DimensionFactory{
        public Dimension newDimension(int w, int h){
            return new Dimension(w, h);
        }
    }

    class ImageIconFactory{
        public ImageIcon newImageIcon(byte[] bytes){
            return new ImageIcon(bytes);
        }
    }

    public Image2CSS(){
        this.cssClassFactory = new CSSClassFactory();
        this.arrayListFactory = new ArrayListFactory();
        this.dimensionFactory = new DimensionFactory();
        this.imageIconFactory = new ImageIconFactory();
        this.base64Encoder = new Base64Encoder();
        this.fileUtils = new FileUtils();
        this.consoleOutput = new ConsoleOutput();
        this.cssOutput = new CSSOutput();
        this.htmlOutput = new HTMLOutput();
        this.reportOutput = (ReportOutput)consoleOutput;
    }

	/**
	 * Begins conversion of image file(s) to CSS classes.
	 * @param parameters
	 * @throws Exception
	 */
	public void execute(Parameters parameters) throws Exception{
		validateParameters(parameters);

        File[] imageFiles = getImagesForConversion(parameters.getImageFile(), parameters.getSupportedTypes());
        List<CSSClass> cssClasses = generateCSSEntries(parameters, imageFiles);

        if(parameters.isOutputToScreen()) consoleOutput.out(parameters, cssClasses);

        if(null != parameters.getCssFile()) cssOutput.out(parameters, cssClasses);

        if(null != parameters.getHtmlFile()) htmlOutput.out(parameters, cssClasses);

        if(parameters.isOutputToScreen()) reportOutput.generateReportOutput(parameters, cssClasses);
	}
	
	/**
	 * Validates inputs.
	 * @param parameters
	 * @throws Exception
	 */
	protected void validateParameters(Parameters parameters) throws Exception{
		if(null == parameters){
			throw new IllegalArgumentException("Parameteres cannot be null");
		}else if(null == parameters.getImageFile()){
			throw new IllegalArgumentException("Image input file or directory must be specified");
		}else if(!parameters.getImageFile().exists()){
			throw new IllegalArgumentException("Image input file or directory not found");
		}else if(null == parameters.getCssFile() && !parameters.isOutputToScreen()){
			throw new IllegalArgumentException("CSS output to file or screen must be specified");
		}
	}

	/**
	 * Builds CSS class entries. CSS class name is the image-filename with all periods replaced by underscores.
	 * @param parameters
	 * @return
	 * @throws IOException
	 */
	protected List<CSSClass> generateCSSEntries(Parameters parameters, File[] imageFiles) throws IOException{
		List<CSSClass> cssEntries = arrayListFactory.newArrayList();
        final String cssClassTemplate = getProperty(CSS_CLASS);

        for(File imageFile : imageFiles){
			final String cssName = imageFile.getName().replaceAll("\\.","_");
            final String imageFileExtension = fileUtils.getExtension(imageFile.getName());
			final byte[] bytes = fileUtils.getFileBytes(imageFile);
            final String base64Bytes = base64Encoder.base64EncodeBytes(bytes);

			Dimension dimension = getImageDimension(bytes);
            int height = (int)dimension.getHeight();
            int width = (int)dimension.getWidth();

            CSSClass cssClass =
                cssClassFactory.newCSSClass(
                    cssName,
                    format(cssClassTemplate, cssName, imageFileExtension, base64Bytes, height, width)
                );

			cssEntries.add(cssClass);
		}

		return cssEntries;
	}

    /**
     * Return the dimensions of an image represented by a byte array.
     * @param bytes
     * @return
     */
    protected Dimension getImageDimension(byte[] bytes){
        ImageIcon imageIcon = imageIconFactory.newImageIcon(bytes);
        return dimensionFactory.newDimension(imageIcon.getIconWidth(), imageIcon.getIconHeight());
    }

    /**
	 * Returns zero, one or many image files based on inputs.
	 * @param imageFile
	 * @param supportedTypes
	 * @return
	 * @throws Exception
	 */
	protected File[] getImagesForConversion(File imageFile, Set<SupportedImageTypes> supportedTypes) throws IOException{
		File[] imagesForConversion;
		if(imageFile.isDirectory()){
			// If none are specified all are indicated
			if(null == supportedTypes || 0 == supportedTypes.size()){
				supportedTypes = new HashSet<SupportedImageTypes>();
				supportedTypes.add(SupportedImageTypes.gif);
				supportedTypes.add(SupportedImageTypes.jpg);
				supportedTypes.add(SupportedImageTypes.png);
			}

			imagesForConversion =
				imageFile
					.listFiles(
						new Image2CssConversionFilenameFilter(supportedTypes)
					);
		}else{
			imagesForConversion = new File[] { imageFile };
		}
		return imagesForConversion;
	}
}