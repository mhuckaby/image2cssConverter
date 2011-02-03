package com.rf1m.util;

import java.awt.Dimension;

import javax.swing.ImageIcon;

import org.apache.commons.codec.binary.Base64;

//import com.sun.org.apache.xml.internal.security.utils.Base64;

public class ByteUtils extends Utils{
	
	/**
	 * Returns a Base64 encoded as single line string.
	 * @param image
	 * @return
	 * @throws Exception
	 */
	public static String base64EncodeBytes(byte[] bytes) throws Exception{
		String encoded = new String(Base64.encodeBase64(bytes, false));
		encoded = encoded.replaceAll(NL, EMPTY);
		return encoded;
	}
	
	/**
	 * Return the dimensions of an image represented by a byte array.
	 * @param bytes
	 * @return
	 */
	public static Dimension getImageDimension(byte[] bytes){
		ImageIcon imageIcon = new ImageIcon(bytes);
		return new Dimension(imageIcon.getIconWidth(), imageIcon.getIconHeight());
	}
}