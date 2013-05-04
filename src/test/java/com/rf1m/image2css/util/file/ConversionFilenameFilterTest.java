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
package com.rf1m.image2css.util.file;

import com.rf1m.image2css.cli.SupportedImageTypes;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import static com.rf1m.image2css.cli.SupportedImageTypes.gif;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConversionFilenameFilterTest {
    final String filenameExtension = "png";

    @Mock
    File directory;

    @Mock
    FileUtils fileUtils;

    ConversionFilenameFilter conversionFilenameFilter;

    Set<SupportedImageTypes> supportedTypes;

    @Before
    public void before(){
        supportedTypes = new HashSet<SupportedImageTypes>();

        when(fileUtils.getExtension(anyString()))
            .thenReturn(filenameExtension);

        conversionFilenameFilter = new ConversionFilenameFilter(fileUtils, supportedTypes);
    }

    @Test
    public void shouldReturnTrueIfAcceptedFilenameIsSupplied(){
        final String acceptedFileName = "kite.gif";

        when(fileUtils.getExtension(acceptedFileName))
            .thenReturn(gif.toString());

        supportedTypes.add(gif);

        final boolean result = conversionFilenameFilter.accept(directory, acceptedFileName);

        assertTrue(result);
    }

    @Test
    public void shouldRejectIfUnAcceptedFilenamesSupplied(){
        final String rejectedFileName = "panda.jpg";

        supportedTypes.add(gif);

        final boolean result = conversionFilenameFilter.accept(directory, rejectedFileName);

        assertFalse(result);
    }

    @Test
    public void shouldRejectFilesWithNoExtension(){
        final String rejectedFileNameWithDot = "kite.";

        supportedTypes.add(gif);

        assertFalse(conversionFilenameFilter.accept(directory, rejectedFileNameWithDot));
        assertFalse(conversionFilenameFilter.accept(directory, rejectedFileNameWithDot));
    }

    @Test
    public void shouldReturnFalseIfThereIsAnIoException() {
        final String filename = "";

        final boolean result = conversionFilenameFilter.accept(directory, filename);
    }

}