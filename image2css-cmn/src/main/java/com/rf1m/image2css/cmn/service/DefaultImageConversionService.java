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
import com.rf1m.image2css.cmn.exception.Errors;
import com.rf1m.image2css.cmn.exception.Image2CssException;
import com.rf1m.image2css.cmn.ioc.CommonObjectFactory;
import com.rf1m.image2css.cmn.util.bin.Base64Encoder;
import com.rf1m.image2css.cmn.util.file.FileUtils;
import org.apache.commons.lang3.tuple.Pair;

import javax.swing.*;
import java.io.*;
import java.net.URL;

import static com.rf1m.image2css.cmn.exception.Errors.errorOpeningStream;
import static java.lang.String.format;

public class DefaultImageConversionService implements ImageConversionService {

    protected final FileUtils fileUtils;
    protected final Base64Encoder base64Encoder;
    protected final CommonObjectFactory commonObjectFactory;
    protected final String cssClassTemplate;

    public DefaultImageConversionService(final FileUtils fileUtils,
                                         final Base64Encoder base64Encoder,
                                         final CommonObjectFactory commonObjectFactory,
                                         final String cssClassTemplate) {
        this.fileUtils = fileUtils;
        this.base64Encoder = base64Encoder;
        this.commonObjectFactory = commonObjectFactory;
        this.cssClassTemplate = cssClassTemplate;
    }

    public CssClass convert(final File imageFile) {
        // TODO Validate parameter
        final String imageFilename = imageFile.getName();
        final String cssClassName = this.determineCssClassName(imageFilename);
        final String fileExtension = this.fileUtils.getExtension(imageFilename);
        final byte[] bytes = this.getFileBytes(imageFile);
        final String base64Bytes = this.base64Encoder.base64EncodeBytes(bytes);
        final Pair<Integer, Integer> dimension = this.getImageDimension(bytes);
        final String cssEntry = this.determineCssEntry(cssClassName, fileExtension, base64Bytes, dimension);
        final CssClass cssClass = this.commonObjectFactory.newCssClass(cssClassName, cssEntry);

        return cssClass;
    }

    protected String determineCssEntry(final String cssClassName,
                                       final String fileExtension,
                                       final String base64Bytes,
                                       final Pair<Integer, Integer> dimension) {
        return format(cssClassTemplate, cssClassName, fileExtension, base64Bytes, dimension.getLeft(), dimension.getRight());
    }

    protected String determineCssClassName(final String fileName) {
        return fileName.replaceAll("\\.","_");
    }

    /**
     * Return the dimension of an image represented by a byte array.
     * @param bytes
     * @return Pair with left as width and right as height.
     */
    protected Pair<Integer, Integer> getImageDimension(final byte[] bytes){
        final ImageIcon imageIcon = this.commonObjectFactory.newImageIcon(bytes);
        final Pair<Integer, Integer> result =
            this.commonObjectFactory.newPair(imageIcon.getIconWidth(), imageIcon.getIconHeight());

        return result;
    }

    /**
     * Read the bytes of a file into a byte array and return the array.
     * @param file
     * @return
     * @throws Exception
     */
    public byte[] getFileBytes(final File file) {
        final FileInputStream fileInputStream = this.commonObjectFactory.newFileInputStream(file);
        final byte[] bytes = this.commonObjectFactory.newByteArray(file.length());

        try {
            fileInputStream.read(bytes);
        }catch(final IOException ioException) {
            throw this.commonObjectFactory.newImage2CssException(ioException, Errors.errorReadingFile);
        }finally {
            try {
                fileInputStream.close();
            }catch(final Exception exception) {
                throw this.commonObjectFactory.newImage2CssException(exception, Errors.errorClosingFile);
            }
        }

        return bytes;
    }

    @Override
    public CssClass convertUrlAsString(final String urlAsString) {
        final URL url = this.commonObjectFactory.newUrl(urlAsString);
        return this.convert(url);
    }

    @Override
    public CssClass convert(final URL url) {
        // TODO Validate parameter
        final String imageFilename = url.getFile();

        // TODO redundant now, consolidate; see this.convert(final File imageFile) {
        final String cssClassName = this.determineCssClassName(imageFilename);
        final String fileExtension = this.fileUtils.getExtension(imageFilename);
        //.

        final BufferedInputStream bufferedInputStream = this.commonObjectFactory.newBufferedInputStream(url);
        final byte[] bytes = this.readInputStreamToBytes(bufferedInputStream);

        // TODO redundant now, consolidate; see this.convert(final File imageFile) {
        final String base64Bytes = this.base64Encoder.base64EncodeBytes(bytes);
        final Pair<Integer, Integer> dimension = this.getImageDimension(bytes);
        final String cssEntry = this.determineCssEntry(cssClassName, fileExtension, base64Bytes, dimension);
        final CssClass cssClass = this.commonObjectFactory.newCssClass(cssClassName, cssEntry);
        return cssClass;
        //.
    }

    protected byte[] readInputStreamToBytes(final BufferedInputStream bufferedInputStream) {
        final ByteArrayOutputStream byteArrayOutputStream = this.commonObjectFactory.newByteArrayOutputStream();
        final byte[] buffer = this.commonObjectFactory.newByteArray(1);

        try {
            int len;
            while((len = bufferedInputStream.read(buffer)) > 0) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            return byteArrayOutputStream.toByteArray();
        }catch(final IOException e) {
            throw this.commonObjectFactory.newImage2CssException(e, errorOpeningStream);
        }
    }

}
