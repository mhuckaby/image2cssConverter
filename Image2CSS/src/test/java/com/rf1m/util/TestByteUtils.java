package com.rf1m.util;

import org.junit.Test;

import java.awt.*;
import java.io.File;
import java.net.URL;

import static junit.framework.Assert.*;

public class TestByteUtils {

	@Test
	public void testBase64EncodeBytes() throws Exception{
		final String content = "test content\nsecond line";
		final String contentB64Encoded = "dGVzdCBjb250ZW50CnNlY29uZCBsaW5l";
		final String testEncoding = ByteUtils.base64EncodeBytes(content.getBytes());

        assertEquals(contentB64Encoded, testEncoding);
	}
	
	@Test
	public void testGetImageDimension() throws Exception{
		final URL url = this.getClass().getResource("/boy_with_book.png");
		final File imageFile = new File(url.getFile());
		
		assertNotNull(imageFile);
		assertTrue(imageFile.exists());
		
		Dimension dimension = ByteUtils.getImageDimension(FileUtils.getFileBytes(imageFile));

        assertEquals(450.0, dimension.getWidth());
		assertEquals(371.0, dimension.getHeight());
	}
}