package com.rf1m.image2css.service;


import com.rf1m.image2css.ioc.CommonObjectFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.spy;

@RunWith(MockitoJUnitRunner.class)
public class DefaultImageConversionServiceTest {
    final String cssClassTemplate = "cssClassTemplate";

    @Mock
    CommonObjectFactory commonObjectFactory;

    DefaultImageConversionService defaultImageConversionService;

    @Before
    public void before() {
    }

    @Test
    public void should() {

    }

//    @Test
//    public void defaultImageConversionServiceShouldConvertFileToBase64EncodedBytes() throws Exception {
//        final String filename = "the.file.gif";
//        final String fileExtension = "gif";
//        final String cssClassName = "the_file_gif";
//        final String cssEntry = "cssEntry";
//        final String base64EncodedBytes = "base64EncodedBytes";
//        final byte[] bytes = {01};
//
//        final File file = mock(File.class);
//        final Pair<Integer, Integer> dimension = mock(Pair.class);
//        final Pair<String, String> filenameAndExtension = mock(Pair.class);
//        final CssClass cssClass = mock(CssClass.class);
//
//        doReturn(filenameAndExtension)
//                .when(defaultImageConversionService)
//                .validateFilenameAndExtension(file);
//
//        when(filenameAndExtension.getLeft())
//                .thenReturn(filename);
//
//        when(filenameAndExtension.getRight())
//                .thenReturn(fileExtension);
//
//        doReturn(cssClassName)
//                .when(defaultImageConversionService)
//                .determineCssClassName(filename);
//
//        doReturn(bytes)
//                .when(defaultImageConversionService)
//                .getFileBytes(file);
//
//        when(base64Encoder.base64EncodeBytes(bytes))
//                .thenReturn(base64EncodedBytes);
//
//        doReturn(dimension)
//                .when(defaultImageConversionService)
//                .getImageDimension(bytes);
//
//        doReturn(cssEntry)
//                .when(defaultImageConversionService)
//                .determineCssEntry(cssClassName, fileExtension, base64EncodedBytes, dimension);
//
//        when(commonObjectFactory.newCssClass(cssClassName, cssEntry))
//                .thenReturn(cssClass);
//
//        final CssClass result = defaultImageConversionService.convert(file);
//
//        assertThat(result, is(cssClass));
//
//        final InOrder inOrder = Mockito.inOrder(file, defaultImageConversionService, base64Encoder, commonObjectFactory);
//
//        inOrder.verify(defaultImageConversionService, times(1))
//                .validateFilenameAndExtension(file);
//
//        inOrder.verify(defaultImageConversionService, times(1))
//                .determineCssClassName(filename);
//
//        inOrder.verify(defaultImageConversionService, times(1))
//                .getFileBytes(file);
//
//        inOrder.verify(base64Encoder, times(1))
//                .base64EncodeBytes(bytes);
//
//        inOrder.verify(defaultImageConversionService, times(1))
//                .getImageDimension(bytes);
//
//        inOrder.verify(defaultImageConversionService, times(1))
//                .determineCssEntry(cssClassName, fileExtension, base64EncodedBytes, dimension);
//
//        inOrder.verify(commonObjectFactory, times(1))
//                .newCssClass(cssClassName, cssEntry);
//    }
//
//    @Test
//    public void shouldReturnPairWithLeftWidthAndRightHeightOfImageBasedOnBytes() {
//        final Pair<Integer, Integer> dimension = mock(Pair.class);
//        final ImageIcon imageIcon = mock(ImageIcon.class);
//
//        final int width = 1;
//        final int height = 2;
//        final byte[] bytes = {01};
//
//        when(commonObjectFactory.newImageIcon(bytes))
//                .thenReturn(imageIcon);
//
//        when(imageIcon.getIconWidth())
//                .thenReturn(width);
//
//        when(imageIcon.getIconHeight())
//                .thenReturn(height);
//
//        when(commonObjectFactory.newPair(width, height))
//                .thenReturn(dimension);
//
//        final Pair<Integer, Integer> result = defaultImageConversionService.getImageDimension(bytes);
//
//        assertThat(result, is(dimension));
//
//        verify(commonObjectFactory, times(1))
//                .newImageIcon(bytes);
//
//        verify(imageIcon, times(1))
//                .getIconWidth();
//
//        verify(imageIcon, times(1))
//                .getIconHeight();
//
//        verify(commonObjectFactory, times(1))
//                .newPair(width, height);
//    }
//
//    @Test
//    public void shouldConvertFilename() {
//        final String before = "a\\b/c.d";
//        final String expected = "a_b_c_d";
//
//        final String result = defaultImageConversionService.determineCssClassName(before);
//
//        assertThat(result, is(expected));
//    }
//
//    @Test
//    public void convertUrlShouldConvertFileToBase64EncodedBytes() throws Exception {
//        final URL url = new URL("http://a.fsdn.com/sd/topics/android_64.png");
//        final String filename = "/sd/topics/android_64.png";
//        final String fileExtension = "png";
//        final String cssClassName = "android_64_png";
//        final String cssEntry = "cssEntry";
//        final String base64EncodedBytes = "base64EncodedBytes";
//        final byte[] bytes = {01};
//
//        final BufferedInputStream bufferedInputStream = mock(BufferedInputStream.class);
//        final Pair<Integer, Integer> dimension = mock(Pair.class);
//        final Pair<String, String> filenameAndExtension = mock(Pair.class);
//        final CssClass cssClass = mock(CssClass.class);
//
//        doNothing()
//                .when(defaultImageConversionService)
//                .validateUrl(url);
//
//        doReturn(filenameAndExtension)
//                .when(defaultImageConversionService)
//                .validateFilenameAndExtension(url);
//
//        when(filenameAndExtension.getLeft())
//                .thenReturn(filename);
//
//        when(filenameAndExtension.getRight())
//                .thenReturn(fileExtension);
//
//        doReturn(cssClassName)
//                .when(defaultImageConversionService)
//                .determineCssClassName(filename);
//
//        when(commonObjectFactory.newBufferedInputStream(any(HttpURLConnection.class)))
//                .thenReturn(bufferedInputStream);
//
//        doReturn(bytes)
//                .when(defaultImageConversionService)
//                .readInputStreamToBytes(bufferedInputStream);
//
//        when(base64Encoder.base64EncodeBytes(bytes))
//                .thenReturn(base64EncodedBytes);
//
//        doReturn(dimension)
//                .when(defaultImageConversionService)
//                .getImageDimension(bytes);
//
//        doReturn(cssEntry)
//                .when(defaultImageConversionService)
//                .determineCssEntry(cssClassName, fileExtension, base64EncodedBytes, dimension);
//
//        when(commonObjectFactory.newCssClass(cssClassName, cssEntry))
//                .thenReturn(cssClass);
//
//        final CssClass result = defaultImageConversionService.convert(url);
//
//        assertThat(result, is(cssClass));
//
//        final InOrder inOrder = inOrder(defaultImageConversionService, base64Encoder, commonObjectFactory);
//
//        inOrder.verify(defaultImageConversionService, times(1))
//                .validateUrl(url);
//
//        inOrder.verify(defaultImageConversionService, times(1))
//                .validateFilenameAndExtension(url);
//
//        inOrder.verify(defaultImageConversionService, times(1))
//                .determineCssClassName(filename);
//
//        inOrder.verify(commonObjectFactory, times(1))
//                .newBufferedInputStream(any(HttpURLConnection.class));
//
//        inOrder.verify(defaultImageConversionService, times(1))
//                .readInputStreamToBytes(bufferedInputStream);
//
//        inOrder.verify(base64Encoder, times(1))
//                .base64EncodeBytes(bytes);
//
//        inOrder.verify(defaultImageConversionService, times(1))
//                .getImageDimension(bytes);
//
//        inOrder.verify(defaultImageConversionService, times(1))
//                .determineCssEntry(cssClassName, fileExtension, base64EncodedBytes, dimension);
//
//        inOrder.verify(commonObjectFactory, times(1))
//                .newCssClass(cssClassName, cssEntry);
//    }
}