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
import com.rf1m.image2css.cmn.service.DefaultImageConversionService;
import com.rf1m.image2css.cmn.service.ImageConversionService;
import com.rf1m.image2css.cmn.util.bin.Base64Encoder;
import com.rf1m.image2css.cmn.util.file.ConversionFilenameFilter;
import com.rf1m.image2css.cmn.util.file.FileUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

import static java.lang.ClassLoader.getSystemResource;

public class CommonObjectFactory extends AbstractFactory<CommonObjectType> {
    protected final String cssClassTemplate = ResourceBundle.getBundle("image2css").getString("template.css.class.def");

    @Override
    protected Class<CommonObjectType> instanceOfCatalogue() {
        return CommonObjectType.class;
    }

    @Override
    protected <T> T createInstanceByFactory(final CommonObjectType value, final Object ... args) {
        switch(value) {
            case arrayList:
                return (T)new ArrayList();

            case base64Encoder:
                return (T)new Base64Encoder(this);

            case byteArray: {
                final Number size = (Number)args[0];
                return (T)new byte[size.intValue()];
            }

            case conversionFilenameFilter: {
                final Set supportedImageTypes = (Set<SupportedImageType>)args[0];
                final FileUtils fileUtils = this.getInstance(CommonObjectType.fileUtils);

                return (T)new ConversionFilenameFilter(fileUtils, supportedImageTypes);
            }

            case cssClass: {
                final String name = (String)args[0];
                final String body = (String)args[1];

                return (T)new CssClass(name, body);
            }

            case defaultImageConversionService: {
                final FileUtils fileUtils = this.getInstance(CommonObjectType.fileUtils);
                final Base64Encoder base64Encoder = this.getInstance(CommonObjectType.base64Encoder);

                final ImageConversionService imageConversionService =
                    new DefaultImageConversionService(fileUtils, base64Encoder, this, cssClassTemplate);

                return (T)imageConversionService;
            }

            case dimension: {
                final int width = (Integer)args[0];
                final int height = (Integer)args[0];

                return (T)new Dimension(width, height);
            }

            case file: {
                final String filename = (String)args[0];
                return (T)new File(filename);
            }

            case fileArray: {
                final File[] files = Arrays.copyOf(args, args.length, File[].class);
                return (T)files;
            }

            case fileInputStream: {
                try {
                    final File file = (File)args[0];
                    return (T)new FileInputStream(file);
                }catch(FileNotFoundException e) {
                    throw new Image2CssException(e, Errors.fileNotFound);
                }
            }

            case fileUtils:
                return (T)new FileUtils(this);

            case fileWriter: {
                try{
                    final File file = (File)args[0];
                    return (T)new FileWriter(file);
                }catch(final IOException ioException) {
                    throw new RuntimeException("Unable to create fileWriter", ioException);
                }
            }

            case imageIcon: {
                final byte[] bytes = ((byte[])args[0]).clone();
                return (T)new ImageIcon(bytes);
            }

            case pair: {
                final Pair<?, ?> result = new ImmutablePair(args[0], args[1]);
                return (T)result;
            }

            case resourceBundle:
                return (T)ResourceBundle.getBundle("image2css");

            case set:
                return (T)new HashSet();

            case string: {
                final byte[] bytes = ((byte[])args[0]).clone();
                return (T)new String(bytes, Charset.defaultCharset());
            }

            case stringArray: {
                final int size = (Integer)args[0];
                return (T)new String[size];
            }

            case stringBuffer:
                return (T)new StringBuffer();

            case supportedImageTypes: {
                final Set<SupportedImageType> result = new HashSet<SupportedImageType>();
                result.add(SupportedImageType.gif);
                result.add(SupportedImageType.jpg);
                result.add(SupportedImageType.png);

                return (T)Collections.unmodifiableSet(result);
            }

            case url: {
                final String filename = (String)args[0];
                return (T)getSystemResource(filename);
            }

            default:
                throw new Image2CssException(Errors.parameterBeanType, value.name());
        }
    }

}