package com.rf1m.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashSet;
import java.util.Set;

import com.rf1m.web.image2css.Parameters.SupportedImageTypes;

/**
 * File utilities used by Image2CSS to convert images into Base64 data for CSS classes.
 * @author Matthew D. Huckaby
 *
 */
public class FileUtils{
	/**
	 * Return the file-extension, null if there is none.
	 * @param path
	 * @return
	 */
	public static String getExtension(String path){
		int lastIndex = path.lastIndexOf('.');
		if(-1 == lastIndex || path.length() == lastIndex){
			return null;
		}else{
			return path.substring(lastIndex+1).toLowerCase();
		}
	}
	
	/**
	 * Read the bytes of a file into a byte array and return the array.
	 * @param image
	 * @return
	 * @throws Exception
	 */
	public static byte[] getFileBytes(File image) throws Exception{
		FileInputStream fis = new FileInputStream(image); 
		byte[] bytes = new byte[(int) image.length()];
		fis.read(bytes);
		fis.close();
		return bytes;
	}

	/**
	 * Returns zero, one or many image files based on inputs.
	 * @param imageFile
	 * @param supportedTypes
	 * @return
	 * @throws Exception
	 */
	public static File[] getImagesForConversion(File imageFile, Set<SupportedImageTypes> supportedTypes) throws Exception{
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