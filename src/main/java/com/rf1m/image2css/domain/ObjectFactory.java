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
package com.rf1m.image2css.domain;

import com.rf1m.image2css.Image2Css;
import com.rf1m.image2css.cli.CommandLineParametersParser;
import com.rf1m.image2css.cli.ImmutableParameters;
import com.rf1m.image2css.cli.MutableParameters;
import com.rf1m.image2css.cli.SupportedImageTypes;
import com.rf1m.image2css.exception.Errors;
import com.rf1m.image2css.exception.Image2CssException;
import com.rf1m.image2css.out.*;
import com.rf1m.image2css.util.PropertiesUtils;
import com.rf1m.image2css.util.bin.Base64Encoder;
import com.rf1m.image2css.util.file.ConversionFilenameFilter;
import com.rf1m.image2css.util.file.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

import static com.rf1m.image2css.exception.Errors.fileNotFoundWhileCreatingFileInputStream;
import static java.lang.ClassLoader.getSystemResource;

public class ObjectFactory {
    protected static final ObjectFactory objectFactory = new ObjectFactory();

    public static ObjectFactory getInstance() {
        return ObjectFactory.objectFactory;
    }

    public <T> T instance(final BeanType beanType, final Object ... args) {
        switch(beanType) {
            case arrayList:
                return (T)new ArrayList();

            case base64Encoder:
                return (T)new Base64Encoder(this);

            case byteArray: {
                final Number size = (Number)args[0];
                return (T)new byte[size.intValue()];
            }

            case commandLineParametersParser:
                return (T)new CommandLineParametersParser(this);

            case consoleOutput:
                return (T)new ConsoleOutput();

            case conversionFilenameFilter: {
                final Set supportedImageTypes = (Set<SupportedImageTypes>)args[0];
                final FileUtils fileUtils = this.instance(BeanType.fileUtils);

                return (T)new ConversionFilenameFilter(fileUtils, supportedImageTypes);
            }

            case cssClass: {
                final String name = (String)args[0];
                final String body = (String)args[1];

                return (T)new CssClass(name, body);
            }

            case cssOutput:
                return (T)new FileOutput(this);

            case defaultPrintStream:
                return (T) System.out;

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
                    throw new Image2CssException(e, fileNotFoundWhileCreatingFileInputStream);
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

            case htmlOutput: {
                final PropertiesUtils propertiesUtils = this.instance(BeanType.propertiesUtils);
                return (T)new HTMLOutput(this, propertiesUtils);
            }

            case image2css: {
                final Base64Encoder base64Encoder = this.instance(BeanType.base64Encoder);
                final FileUtils fileUtils = this.instance(BeanType.fileUtils);
                final PropertiesUtils propertiesUtils = this.instance(BeanType.propertiesUtils);
                final Output consoleOutput = this.instance(BeanType.consoleOutput);
                final Output cssOutput = this.instance(BeanType.cssOutput);
                final Output htmlOutput = this.instance(BeanType.htmlOutput);
                final ReportOutput reportOutput = this.instance(BeanType.consoleOutput);

                return (T)new Image2Css(this, base64Encoder, fileUtils, propertiesUtils, consoleOutput, cssOutput, htmlOutput, reportOutput);
            }

            case imageIcon: {
                final byte[] bytes = ((byte[])args[0]).clone();
                return (T)new ImageIcon(bytes);
            }

            case immutableParameters: {
                final File imageFile = (File)args[0];
                final File cssFile = (File)args[1];
                final File htmlFile = (File)args[2];
                final Set<SupportedImageTypes> supportedImageTypes = (Set<SupportedImageTypes>)args[3];
                final boolean outputToScreen = (Boolean)args[4];
                return (T)new ImmutableParameters(imageFile, cssFile, htmlFile, supportedImageTypes, outputToScreen);
            }

            case mutableParameters:
                return (T)new MutableParameters();

            case properties:
                return (T)new Properties();

            case propertiesUtils:
                return (T)new PropertiesUtils(this);

            case reportOutput:
                return (T)(ReportOutput)this.instance(BeanType.consoleOutput);

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
                final Set<SupportedImageTypes> result = new HashSet<SupportedImageTypes>();
                result.add(SupportedImageTypes.gif);
                result.add(SupportedImageTypes.jpg);
                result.add(SupportedImageTypes.png);

                return (T)Collections.unmodifiableSet(result);
            }

            case url: {
                final String filename = (String)args[0];
                return (T)getSystemResource(filename);
            }

            default:
                throw new Image2CssException(Errors.parameterBeanType, beanType.name());
        }
    }

}