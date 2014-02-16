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

import com.rf1m.image2css.cli.ImmutableParameters;
import com.rf1m.image2css.domain.SupportedImageType;
import com.rf1m.image2css.cmn.exception.Errors;
import com.rf1m.image2css.cmn.exception.Image2CssValidationException;
import com.rf1m.image2css.cmn.ioc.CommonObjectFactory;
import com.rf1m.image2css.cmn.util.file.FileUtils;

import java.io.File;
import java.net.URL;
import java.util.Set;


public class CliObjectFactory extends CommonObjectFactory {

    public CliObjectFactory(final FileUtils fileUtils) {
        super(fileUtils);
    }

    public ImmutableParameters newImmutableParameters(final File imageFile,
                                                      final File cssFile,
                                                      final File htmlFile,
                                                      final Set<SupportedImageType> supportedImageTypes,
                                                      final boolean outputToScreen,
                                                      final boolean isLocalResource,
                                                      final URL url) {

        return new ImmutableParameters(imageFile, cssFile, htmlFile, supportedImageTypes, outputToScreen, isLocalResource, url);
    }

    public Image2CssValidationException newImage2CssValidationException(final Errors errors) {
        return new Image2CssValidationException(errors);
    }

}
