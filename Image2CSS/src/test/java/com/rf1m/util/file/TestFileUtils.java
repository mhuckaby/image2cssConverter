package com.rf1m.util.file;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestFileUtils {
    FileUtils fileUtils;

    @Mock FileUtils.FileInputStreamFactory fileInputStreamFactory;
    @Mock FileUtils.ByteSizer byteSizer;
    @Mock File file;
    @Mock FileInputStream fileInputStream;

    final Number fileLength = 3;
    final byte[] bytes = {1,2,3};//new byte[fileLength.intValue()];

    @Before
    public void before() throws IOException{
        fileUtils = new FileUtils();

        fileUtils.fileInputStreamFactory = fileInputStreamFactory;
        fileUtils.byteSizer = byteSizer;

        when(fileInputStreamFactory.newFileInputStream(file)).thenReturn(fileInputStream);
        when(file.length()).thenReturn(fileLength.longValue());
        when(byteSizer.size(file.length())).thenReturn(bytes);
    }

	@Test
	public void testGetExtension() throws Exception{
        final String path = "/some.file.png";
        assertThat(fileUtils.getExtension(path), is("png"));
	}

    @Test
    public void fileHasNoExtensionShouldReturnEmptyString(){
        final String pathWithNoExtension = "abc";
        assertThat(fileUtils.getExtension(pathWithNoExtension), is(""));
    }

    @Test
    public void fileWithNoExtensionAfterDotShouldReturnEmptyString(){
        final String pathWithNoExtension = "abc.";
        assertThat(fileUtils.getExtension(pathWithNoExtension), is(""));
    }

    @Test
    public void getFileBytes() throws IOException{
        byte[] result = fileUtils.getFileBytes(file);

        verify(fileInputStream).read(bytes);
        verify(fileInputStream).close();

        assertThat(result, is(bytes));
    }
}