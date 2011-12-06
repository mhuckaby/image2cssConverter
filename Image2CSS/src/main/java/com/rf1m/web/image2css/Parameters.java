package com.rf1m.web.image2css;

import java.io.File;
import java.util.Set;

/**
 * Provides Image2CSS information about inputs and outputs.
 * @author Matthew D. Huckaby
 *
 */
public interface Parameters{
	public enum SupportedImageTypes{
		gif,
		jpg,
		jpeg,
		png
	}

	/**
	 * Method to indicate to execution whether output should be displayed on the console.s
	 * @return boolean, 
	 */
	public boolean isOutputToScreen();
	
	/**
	 * Directory of image-files or single image file to be converted to a single CSS class file.
	 * @return
	 */
	public File getImageFile();
	
	/**
	 * CSS file where the image data will be written as CSS classes.
	 * @return
	 */
	public File getCssFile();
	
	/**
	 * HTML file where the generated CSS classes are demonstrated.
	 * @return
	 */
	public File getHtmlFile();
	
	/**
	 * Image types that will be included in filter when image-file is a directory.
	 * @return
	 */
	public Set<SupportedImageTypes> getSupportedTypes();
}
