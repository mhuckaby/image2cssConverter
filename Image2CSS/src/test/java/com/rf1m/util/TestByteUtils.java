package com.rf1m.util;

import static junit.framework.Assert.*;

import java.awt.Dimension;
import java.io.File;
import java.net.URL;

import org.junit.Test;

public class TestByteUtils {

	@Test
	public void testBase64EncodeBytes() throws Exception{
		String content = "test content\nsecond line";
		String contentB64Encoded = "dGVzdCBjb250ZW50CnNlY29uZCBsaW5l";
		String testEncoding = ByteUtils.base64EncodeBytes(content.getBytes());
		assertEquals(contentB64Encoded, testEncoding);
	}
	
	@Test
	public void testGetImageDimension() throws Exception{
		URL url = this.getClass().getResource("/boy_with_book.png");
		File imageFile = new File(url.getFile());
		
		assertNotNull(imageFile);
		assertTrue(imageFile.exists());
		
		Dimension dimension = ByteUtils.getImageDimension(FileUtils.getFileBytes(imageFile));
		assertEquals(450.0, dimension.getWidth());
		assertEquals(371.0, dimension.getHeight());
	}
}
