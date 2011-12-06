package com.rf1m.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Set;

import com.rf1m.web.image2css.Parameters.SupportedImageTypes;

/**
 * Filename filter used to limit which image files are converted.
 * @author Matthew D. Huckaby
 *
 */
public class Image2CssConversionFilenameFilter implements FilenameFilter{
	Set<SupportedImageTypes> supportedTypes;
	
	public Image2CssConversionFilenameFilter(Set<SupportedImageTypes> supportedTypes){
		this.supportedTypes = supportedTypes; 
	}
	
	@Override
	public boolean accept(File fileDir, String filename) {
		String extension = FileUtils.getExtension(filename);
		if(null != extension){
			try{
				if(supportedTypes.contains( SupportedImageTypes.valueOf(FileUtils.getExtension(filename))) ){
					return true;
				}else{
					return false;
				}
			}catch(IllegalArgumentException e){
				return false;
			}
		}else{
			return false;
		}
	}
}