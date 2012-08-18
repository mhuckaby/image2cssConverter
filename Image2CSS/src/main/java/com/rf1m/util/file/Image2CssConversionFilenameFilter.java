package com.rf1m.util.file;

import com.rf1m.web.image2css.cli.SupportedImageTypes;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Set;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * Filename filter used to limit which image files are converted.
 * @author Matthew D. Huckaby
 *
 */
public class Image2CssConversionFilenameFilter implements FilenameFilter{
    protected FileUtils fileUtils;
	protected Set<SupportedImageTypes> supportedTypes;

	public Image2CssConversionFilenameFilter(Set<SupportedImageTypes> supportedTypes){
		this.supportedTypes = supportedTypes;
        this.fileUtils = new FileUtils();
	}
	
	@Override
	public boolean accept(File fileDir, String filename) {
		String extension = fileUtils.getExtension(filename);
		if(isNotEmpty(extension)){
			try{
				if(supportedTypes.contains( SupportedImageTypes.valueOf(fileUtils.getExtension(filename))) ){
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