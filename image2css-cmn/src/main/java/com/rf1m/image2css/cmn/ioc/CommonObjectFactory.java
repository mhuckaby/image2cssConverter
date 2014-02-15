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
package com.rf1m.image2css.cmn.ioc;

import com.rf1m.image2css.cmn.domain.CssClass;
import com.rf1m.image2css.cmn.domain.SupportedImageType;
import com.rf1m.image2css.cmn.exception.Errors;
import com.rf1m.image2css.cmn.exception.Image2CssException;
import com.rf1m.image2css.cmn.util.file.ConversionFilenameFilter;
import com.rf1m.image2css.cmn.util.file.FileUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.rf1m.image2css.cmn.exception.Errors.errorCreatingUrlFromStringValue;
import static com.rf1m.image2css.cmn.exception.Errors.errorOpeningStream;

public class CommonObjectFactory {
    protected final FileUtils fileUtils;

    public CommonObjectFactory(final FileUtils fileUtils) {
        this.fileUtils = fileUtils;
    }

    public List newMutableList() {
        return new ArrayList();
    };

    public Set newMutableSet() {
        return new HashSet();
    }

    public byte[] newByteArray(final long size) {
        // Ick. File.length() => long, but byte[arg is int].
        return this.newByteArray((int)size);
    }

    public byte[] newByteArray(final int size) {
        return new byte[size];
    }

    public ConversionFilenameFilter newConversionFilenameFilter(final Set<SupportedImageType> supportedImageTypes) {
        return new ConversionFilenameFilter(this.fileUtils, supportedImageTypes);
    }

    public CssClass newCssClass(final String name, final String body) {
        return new CssClass(name, body);
    }

    public Dimension newDimension(final int width, final int height) {
        return new Dimension(width, height);
    }

    public File newFile(final String filename) {
        return new File(filename);
    }

    public FileInputStream newFileInputStream(final File file) {
        try {
            return new FileInputStream(file);
        }catch(final FileNotFoundException e) {
            throw this.newImage2CssException(e, Errors.fileNotFound);
        }
    }

    public FileWriter newFileWriter(final File file) {
        try {
            return new FileWriter(file);
        }catch(final IOException ioException) {
            throw this.newImage2CssException(ioException, Errors.errorCreatingFileWriter);
        }
    }

    public ImageIcon newImageIcon(final byte[] content) {
        final byte[] bytes = content.clone();
        return new ImageIcon(bytes);
    }

    public Pair newPair(final Object key, final Object value) {
        return new ImmutablePair(key, value);
    }

    public String newString(final byte[] content) {
        final byte[] bytes = content.clone();
        return new String(bytes, Charset.defaultCharset());
    }

    public StringBuffer newStringBuffer() {
        return new StringBuffer();
    }

    public URL newUrl(final String url) {
        try {
            return new URL(url);
        }catch(final MalformedURLException e) {
            throw this.newImage2CssException(e, errorCreatingUrlFromStringValue);
        }
    }

    public Image2CssException newImage2CssException(final Errors errors) {
        return new Image2CssException(errors);
    }

    public Image2CssException newImage2CssException(final Throwable cause, final Errors errors) {
        return new Image2CssException(cause, errors);
    }

    public BufferedInputStream newBufferedInputStream(final URL url) throws Image2CssException {
        try {
            return new BufferedInputStream(url.openStream());
        }catch(final IOException e) {
            throw this.newImage2CssException(e, errorOpeningStream);
        }
    }

    public ByteArrayOutputStream newByteArrayOutputStream() {
        return new ByteArrayOutputStream();
    }

}