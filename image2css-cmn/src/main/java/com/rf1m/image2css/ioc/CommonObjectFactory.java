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
package com.rf1m.image2css.ioc;

import com.rf1m.image2css.domain.CssClass;
import com.rf1m.image2css.domain.SupportedImageType;
import com.rf1m.image2css.exception.Errors;
import com.rf1m.image2css.exception.Image2CssException;
import com.rf1m.image2css.exception.Image2CssValidationException;
import com.rf1m.image2css.util.ConversionFilenameFilter;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.rf1m.image2css.exception.Errors.errorCreatingUrlFromStringValue;
import static com.rf1m.image2css.exception.Errors.errorOpeningStream;

public class CommonObjectFactory {

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
        return new ConversionFilenameFilter(supportedImageTypes);
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

    public StringBuffer newStringBuffer() {
        return new StringBuffer();
    }

    public StringBuilder newStringBuilder(final String initialValue) {
        return new StringBuilder(initialValue);
    }

    public Image2CssException newImage2CssValidationException(final Errors errors) {
        return new Image2CssValidationException(errors);
    }

    public Image2CssException newImage2CssValidationException(final Throwable cause, final Errors errors) {
        return new Image2CssValidationException(cause, errors);
    }

    public Image2CssException newImage2CssException(final Throwable cause, final Errors errors) {
        return new Image2CssException(cause, errors);
    }

    public BufferedInputStream newBufferedInputStream(final HttpURLConnection urlConn) throws Image2CssException {
        try {
            return new BufferedInputStream(urlConn.getInputStream());
        }catch(final IOException e) {
            throw this.newImage2CssException(e, errorOpeningStream);
        }
    }

    public ByteArrayOutputStream newByteArrayOutputStream() {
        return new ByteArrayOutputStream();
    }

    public <L, R> Pair<L, R> newPair(L l, R r) {
        return new ImmutablePair<L, R>(l, r);
    }

}