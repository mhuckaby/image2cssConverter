package com.rf1m.web.image2css.cli;

import java.io.File;
import java.util.Set;

/**
 * Provides Image2CSS information about inputs and outputs.
 * @author Matthew D. Huckaby
 *
 */
public interface Parameters{
	/**
	 * Method to indicate to execution whether output should be displayed on the console.s
	 * @return boolean, 
	 */
	boolean isOutputToScreen();
	
	/**
	 * Directory of image-files or single image file to be converted to a single CSS class file.
	 * @return
	 */
	File getImageFile();
	
	/**
	 * CSS file where the image data will be written as CSS classes.
	 * @return
	 */
	File getCssFile();

	/**
	 * HTML file where the generated CSS classes are demonstrated.
	 * @return
	 */
	File getHtmlFile();

	/**
	 * Image types that will be included in filter when image-file is a directory.
	 * @return
	 */
	Set<SupportedImageTypes> getSupportedTypes();
}
