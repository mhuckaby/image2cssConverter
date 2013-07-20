/**
 *
 * Copyright (c) 2011 Matthew D Huckaby. All rights reservered.
 * ------------------------------------------------------------------------------------
 * Image2Css is licensed under Apache 2.0, please see LICENSE file.
 *
 * Use of this software indicates you agree to the following as well :
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 * This product includes software developed by The Apache Software Foundation (http://www.apache.org/).
 * ------------------------------------------------------------------------------------
 */
package com.rf1m.image2css.cmn.service;

import com.rf1m.image2css.cmn.domain.CssClass;
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

        final File file = mock(File.class);
        final Pair<Integer, Integer> dimension = mock(Pair.class);
        final CssClass cssClass = mock(CssClass.class);

        when(file.getName())
            .thenReturn(filename);

        doReturn(cssClassName)
            .when(defaultImageConversionService)
            .determineCssClassName(filename);

        when(fileUtils.getExtension(filename))
            .thenReturn(fileExtension);

        doReturn(bytes)
            .when(defaultImageConversionService)
            .getFileBytes(file);

        when(base64Encoder.base64EncodeBytes(bytes))
            .thenReturn(base64EncodedBytes);

        doReturn(dimension)
            .when(defaultImageConversionService)
            .getImageDimension(bytes);

        doReturn(cssEntry)
            .when(defaultImageConversionService)
            .determineCssEntry(cssClassName, fileExtension, base64EncodedBytes, dimension);

        when(commonObjectFactory.newCssClass(cssClassName, cssEntry))
            .thenReturn(cssClass);

        final CssClass result = defaultImageConversionService.convert(file);

        assertThat(result, is(cssClass));

        final InOrder inOrder = inOrder(file, defaultImageConversionService, fileUtils, fileUtils, base64Encoder,
            defaultImageConversionService, defaultImageConversionService, commonObjectFactory);

        inOrder.verify(file, times(1))
            .getName();

        inOrder.verify(defaultImageConversionService, times(1))
            .determineCssClassName(filename);

        inOrder.verify(fileUtils, times(1))
            .getExtension(filename);

        inOrder.verify(defaultImageConversionService, times(1))
            .getFileBytes(file);

        inOrder.verify(base64Encoder, times(1))
            .base64EncodeBytes(bytes);

        inOrder.verify(defaultImageConversionService, times(1))
            .getImageDimension(bytes);

        inOrder.verify(defaultImageConversionService, times(1))
            .determineCssEntry(cssClassName, fileExtension, base64EncodedBytes, dimension);

        inOrder.verify(commonObjectFactory, times(1))
            .newCssClass(cssClassName, cssEntry);
    }

    @Test
    public void shouldReturnPairWithLeftWidthAndRightHeightOfImageBasedOnBytes() {
        final Pair<Integer, Integer> dimension = mock(Pair.class);
        final ImageIcon imageIcon = mock(ImageIcon.class);

        final int width = 1;
        final int height = 2;
        final byte[] bytes = {01};

        when(commonObjectFactory.newImageIcon(bytes))
            .thenReturn(imageIcon);

        when(imageIcon.getIconWidth())
            .thenReturn(width);

        when(imageIcon.getIconHeight())
            .thenReturn(height);

        when(commonObjectFactory.newPair(width, height))
            .thenReturn(dimension);

        final Pair<Integer, Integer> result = defaultImageConversionService.getImageDimension(bytes);

        assertThat(result, is(dimension));

        verify(commonObjectFactory, times(1))
            .newImageIcon(bytes);

        verify(imageIcon, times(1))
            .getIconWidth();

        verify(imageIcon, times(1))
            .getIconHeight();

        verify(commonObjectFactory, times(1))
            .newPair(width, height);
    }

    @Test
    public void shouldConvertFilename() {
        final String before = "a.b.c";
        final String expected = "a_b_c";

        final String result = defaultImageConversionService.determineCssClassName(before);

        assertThat(result, is(expected));
    }

}
