package com.rf1m.image2css.cmn.service;

import com.rf1m.image2css.cmn.domain.CssClass;
import com.rf1m.image2css.cmn.ioc.CommonObjectType;
import com.rf1m.image2css.cmn.ioc.CommonObjectFactory;
import com.rf1m.image2css.cmn.util.bin.Base64Encoder;
import com.rf1m.image2css.cmn.util.file.FileUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.swing.*;
import java.io.File;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DefaultImageConversionServiceTest {
    final String cssClassTemplate = "cssClassTemplate";

    @Mock
    CommonObjectFactory commonObjectFactory;

    @Mock
    Base64Encoder base64Encoder;

    @Mock
    FileUtils fileUtils;

    DefaultImageConversionService defaultImageConversionService;

    @Before
    public void before() {
        defaultImageConversionService = spy(new DefaultImageConversionService(fileUtils, base64Encoder, commonObjectFactory, cssClassTemplate));
    }

    @Test
    public void defaultImageConversionServiceShouldConvertFileToBase64EncodedBytes() throws Exception {
        final String filename = "the.file.gif";
        final String fileExtension = "gif";
        final String cssClassName = "the_file_gif";
        final String cssEntry = "cssEntry";
        final String base64EncodedBytes = "base64EncodedBytes";
        final byte[] bytes = {01};

        File file = mock(File.class);
        Pair<Integer, Integer> dimension = mock(Pair.class);
        CssClass cssClass = mock(CssClass.class);

        when(file.getName())
            .thenReturn(filename);

        doReturn(cssClassName)
            .when(defaultImageConversionService)
            .determineCssClassName(filename);

        when(fileUtils.getExtension(filename))
            .thenReturn(fileExtension);

        when(fileUtils.getFileBytes(file))
            .thenReturn(bytes);

        when(base64Encoder.base64EncodeBytes(bytes))
            .thenReturn(base64EncodedBytes);

        doReturn(dimension)
            .when(defaultImageConversionService)
            .getImageDimension(bytes);

        doReturn(cssEntry)
            .when(defaultImageConversionService)
            .determineCssEntry(cssClassName, fileExtension, base64EncodedBytes, dimension);

        when(commonObjectFactory.getInstance(CommonObjectType.cssClass, cssClassName, cssEntry))
            .thenReturn(cssClass);

        final CssClass result = defaultImageConversionService.convert(file);

        assertThat(result, is(cssClass));

        InOrder inOrder = inOrder(file, defaultImageConversionService, fileUtils, fileUtils, base64Encoder,
            defaultImageConversionService, defaultImageConversionService, commonObjectFactory);

        inOrder.verify(file, times(1))
            .getName();

        inOrder.verify(defaultImageConversionService, times(1))
            .determineCssClassName(filename);

        inOrder.verify(fileUtils, times(1))
            .getExtension(filename);

        inOrder.verify(fileUtils, times(1))
            .getFileBytes(file);

        inOrder.verify(base64Encoder, times(1))
            .base64EncodeBytes(bytes);

        inOrder.verify(defaultImageConversionService, times(1))
            .getImageDimension(bytes);

        inOrder.verify(defaultImageConversionService, times(1))
            .determineCssEntry(cssClassName, fileExtension, base64EncodedBytes, dimension);

        inOrder.verify(commonObjectFactory, times(1))
            .getInstance(CommonObjectType.cssClass, cssClassName, cssEntry);
    }

    @Test
    public void shouldReturnPairWithLeftWidthAndRightHeightOfImageBasedOnBytes() {
        Pair<Integer, Integer> dimension = mock(Pair.class);
        ImageIcon imageIcon = mock(ImageIcon.class);

        final int width = 1;
        final int height = 2;
        final byte[] bytes = {01};

        when(commonObjectFactory.getInstance(CommonObjectType.imageIcon, bytes))
            .thenReturn(imageIcon);

        when(imageIcon.getIconWidth())
            .thenReturn(width);

        when(imageIcon.getIconHeight())
            .thenReturn(height);

        when(commonObjectFactory.getInstance(CommonObjectType.pair, width, height))
            .thenReturn(dimension);

        final Pair<Integer, Integer> result = defaultImageConversionService.getImageDimension(bytes);

        assertThat(result, is(dimension));

        verify(commonObjectFactory, times(1))
            .getInstance(CommonObjectType.imageIcon, bytes);

        verify(imageIcon, times(1))
            .getIconWidth();

        verify(imageIcon, times(1))
            .getIconHeight();

        verify(commonObjectFactory, times(1))
            .getInstance(CommonObjectType.pair, width, height);
    }

    @Test
    public void shouldConvertFilename() {
        final String before = "a.b.c";
        final String expected = "a_b_c";

        final String result = defaultImageConversionService.determineCssClassName(before);

        assertThat(result, is(expected));
    }

}
