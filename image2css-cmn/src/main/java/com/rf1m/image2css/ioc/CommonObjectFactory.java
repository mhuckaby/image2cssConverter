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

import com.rf1m.image2css.domain.SupportedImageType;
import com.rf1m.image2css.exception.Errors;
import com.rf1m.image2css.exception.Image2CssException;
import com.rf1m.image2css.exception.Image2CssValidationException;
import com.rf1m.image2css.util.ConversionFilenameFilter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommonObjectFactory {

    public List newMutableList() {
        return new ArrayList();
    };

    public Set newMutableSet() {
        return new HashSet();
    }

    public ConversionFilenameFilter newConversionFilenameFilter(final Set<SupportedImageType> supportedImageTypes) {
        return new ConversionFilenameFilter(supportedImageTypes);
    }

    public File newFile(final String filename) {
        return new File(filename);
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
}