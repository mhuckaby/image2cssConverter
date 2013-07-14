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
package com.rf1m.image2css.cmn.util.file;

import com.rf1m.image2css.cmn.domain.SupportedImageType;
import com.rf1m.image2css.cmn.ioc.CommonObjectFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FileUtilsTest {
    @Mock
    CommonObjectFactory commonObjectFactory;

    @Mock
    Set<SupportedImageType> supportedImageTypes;

    @Mock
    File file;

    @Mock
    FileInputStream fileInputStream;

    final byte[] bytes = {1,2,3};

    FileUtils fileUtils;

    @Before
    public void before() throws IOException{
        fileUtils = new FileUtils(commonObjectFactory, supportedImageTypes);

        when(commonObjectFactory.newFileInputStream(file))
            .thenReturn(fileInputStream);

        when(file.length())
            .thenReturn((long)bytes.length);

        when(commonObjectFactory.newByteArray((long)bytes.length))
            .thenReturn(bytes);

        when(file.length())
            .thenReturn((long) bytes.length);
    }

	@Test
	public void testGetExtension() throws Exception{
        final String path = "/some.file.png";
        final String result = fileUtils.getExtension(path);

        assertThat(result, is("png"));
	}

    @Test
    public void fileHasNoExtensionShouldReturnEmptyString(){
        final String pathWithNoExtension = "abc";
        final String result = fileUtils.getExtension(pathWithNoExtension);

        assertThat(result, is(""));
    }

    @Test
    public void fileWithNoExtensionAfterDotShouldReturnEmptyString(){
        final String pathWithNoExtension = "abc.";
        final String result = fileUtils.getExtension(pathWithNoExtension);

        assertThat(result, is(""));
    }

    @Test
    public void getFileBytes() throws IOException{
        byte[] result = fileUtils.getFileBytes(file);

        verify(fileInputStream).read(bytes);
        verify(fileInputStream).close();

        assertThat(result, is(bytes));
    }

}