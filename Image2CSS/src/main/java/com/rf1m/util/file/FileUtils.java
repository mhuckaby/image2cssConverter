package com.rf1m.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.rf1m.web.image2css.cli.SupportedImageTypes;
import sun.java2d.pipe.BufferedTextPipe;

/**
 * File utilities used by Image2CSS to convert images into Base64 data for CSS classes.
 * @author Matthew D. Huckaby
 *
 */
public class FileUtils{
    protected FileInputStreamFactory fileInputStreamFactory;
    protected ByteSizer byteSizer;

    class FileInputStreamFactory{
        public FileInputStream newFileInputStream(File file) throws IOException{
            return new FileInputStream(file);
        }
    }

    class ByteSizer{
        public byte[] size(Number i){
            return new byte[i.intValue()];
        }
    }

    public FileUtils() {
        this.fileInputStreamFactory = new FileInputStreamFactory();
        this.byteSizer = new ByteSizer();
    }

    /**
	 * Return the file-extension, null if there is none.
	 * @param path
	 * @return
	 */
	public String getExtension(String path){
		int lastIndex = path.lastIndexOf('.');
		if(-1 == lastIndex || path.length() == lastIndex){
			return "";
		}else{
			return path.substring(lastIndex+1).toLowerCase();
		}
	}

	/**
	 * Read the bytes of a file into a byte array and return the array.
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public byte[] getFileBytes(File file) throws IOException{
		FileInputStream fileInputStream = fileInputStreamFactory.newFileInputStream(file);
		byte[] bytes = byteSizer.size(file.length());
		fileInputStream.read(bytes);
		fileInputStream.close();
		return bytes;
	}
}