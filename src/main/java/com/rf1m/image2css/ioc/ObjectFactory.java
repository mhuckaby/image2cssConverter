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

import com.rf1m.image2css.Image2Css;
import com.rf1m.image2css.cli.*;
import com.rf1m.image2css.domain.CssClass;
import com.rf1m.image2css.exception.Errors;
import com.rf1m.image2css.exception.Image2CssException;
import com.rf1m.image2css.out.*;
import com.rf1m.image2css.util.bin.Base64Encoder;
import com.rf1m.image2css.util.file.ConversionFilenameFilter;
import com.rf1m.image2css.util.file.FileUtils;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

import static com.rf1m.image2css.exception.Errors.fileNotFoundWhileCreatingFileInputStream;
import static java.lang.ClassLoader.getSystemResource;

public class ObjectFactory {
    protected static final ObjectFactory objectFactory = new ObjectFactory();

    protected final String cssClassTemplate = ResourceBundle.getBundle("image2css").getString("template.css.class.def");
    protected final String htmlIndexTemplate = ResourceBundle.getBundle("image2css").getString("template.html.index");
    protected final String htmlCssEntryTemplate = ResourceBundle.getBundle("image2css").getString("template.html.css.entry");

    public static ObjectFactory getInstance() {
        return ObjectFactory.objectFactory;
    }

    public <T> T instance(final BeanType beanType, final Object ... args) {
        switch(beanType) {
            case arrayList:
                return (T)new ArrayList();

            case base64Encoder:
                return (T)new Base64Encoder(this);

            case basicParser:
                return (T)new BasicParser();

            case byteArray: {
                final Number size = (Number)args[0];
                return (T)new byte[size.intValue()];
            }

            case commandLineParametersParser:
                return (T)new CommandLineParametersParser(this);

            case commandLineRunner: {
                final PrintStream printStream = this.instance(BeanType.defaultPrintStream);
                final ResourceBundle resourceBundle = this.instance(BeanType.resourceBundle);

                return (T)new CommandLineRunner(this, printStream, resourceBundle);
            }

            case consoleOutput:
                final ResourceBundle resourceBundle = this.instance(BeanType.resourceBundle);
                return (T)new ConsoleOutput(resourceBundle, System.out);

            case conversionFilenameFilter: {
                final Set supportedImageTypes = (Set<SupportedImageType>)args[0];
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
                return (T)new HTMLOutput(this, htmlCssEntryTemplate, htmlIndexTemplate);
            }

            case image2css: {
                final Base64Encoder base64Encoder = this.instance(BeanType.base64Encoder);
                final FileUtils fileUtils = this.instance(BeanType.fileUtils);
                final Output consoleOutput = this.instance(BeanType.consoleOutput);
                final Output cssOutput = this.instance(BeanType.cssOutput);
                final Output htmlOutput = this.instance(BeanType.htmlOutput);
                final ReportOutput reportOutput = this.instance(BeanType.consoleOutput);

                return (T)new Image2Css(this, base64Encoder, fileUtils, consoleOutput, cssOutput, htmlOutput, reportOutput, cssClassTemplate);
            }

            case imageIcon: {
                final byte[] bytes = ((byte[])args[0]).clone();
                return (T)new ImageIcon(bytes);
            }

            case immutableParameters: {
                final File imageFile = (File)args[0];
                final File cssFile = (File)args[1];
                final File htmlFile = (File)args[2];
                final Set<SupportedImageType> supportedImageTypes = (Set<SupportedImageType>)args[3];
                final boolean outputToScreen = (Boolean)args[4];
                return (T)new ImmutableParameters(imageFile, cssFile, htmlFile, supportedImageTypes, outputToScreen);
            }

            case optionCssFile: {
                final ResourceBundle objResourceBundle = objectFactory.instance(BeanType.resourceBundle);
                final String command = objResourceBundle.getString("command.line.option.cmd.css.output.filename");
                final String description = objResourceBundle.getString("command.line.option.description.css.output.filename");

                final Option option = new Option(command, true, description);
                option.setRequired(true);

                return (T)option;
            }

            case optionHtmlFile: {
                final ResourceBundle objResourceBundle = objectFactory.instance(BeanType.resourceBundle);
                final String command = objResourceBundle.getString("command.line.option.cmd.html.output.filename");
                final String description = objResourceBundle.getString("command.line.option.description.html.output.filename");

                final Option option = new Option(command, true, description);
                option.setRequired(true);

                return (T)option;
            }

            case optionImageFile: {
                final ResourceBundle objResourceBundle = objectFactory.instance(BeanType.resourceBundle);
                final String command = objResourceBundle.getString("command.line.option.cmd.target.file.or.directory");
                final String description = objResourceBundle.getString("command.line.option.description.target.file.or.directory");

                final Option option = new Option(command, true, description);
                option.setRequired(true);
                option.setOptionalArg(false);

                return (T)option;
            }

            case optionImageTypes: {
                final ResourceBundle objResourceBundle = objectFactory.instance(BeanType.resourceBundle);
                final String command = objResourceBundle.getString("command.line.option.cmd.included.image.types");
                final String description = objResourceBundle.getString("command.line.option.description.included.image.types");

                final Option option = new Option(command, true, description);
                option.setValueSeparator(' ');
                option.setArgs(3);

                return (T)option;
            }

            case optionSyso: {
                final ResourceBundle objResourceBundle = objectFactory.instance(BeanType.resourceBundle);
                final String command = objResourceBundle.getString("command.line.option.cmd.output.to.screen");
                final String description = objResourceBundle.getString("command.line.option.description.output.to.screen");

                final Option option = new Option(command, false, description);

                return (T)option;
            }

            case options: {
                final Option cssFileOption = objectFactory.instance(BeanType.optionCssFile);
                final Option htmlFileOption = objectFactory.instance(BeanType.optionHtmlFile);
                final Option imageFileOption = objectFactory.instance(BeanType.optionImageFile);
                final Option imageTypesOption = objectFactory.instance(BeanType.optionImageTypes);
                final Option sysoOption = objectFactory.instance(BeanType.optionSyso);

                final Options options = new Options();

                options.addOption(cssFileOption);
                options.addOption(htmlFileOption);
                options.addOption(imageFileOption);
                options.addOption(imageTypesOption);
                options.addOption(sysoOption);

                return (T)options;
            }

            case helpFormatter: {
                final ResourceBundle objResourceBundle = objectFactory.instance(BeanType.resourceBundle);
                final String helpText = objResourceBundle.getString("command.line.help.text");
                final Options image2cssOptions = objectFactory.instance(BeanType.options);
                final Image2CssHelpFormatter image2CssHelpFormatter = new Image2CssHelpFormatter(helpText, image2cssOptions);

                image2CssHelpFormatter.setWidth(85);
                return (T)image2CssHelpFormatter;
            }

            case reportOutput:
                return (T)(ReportOutput)this.instance(BeanType.consoleOutput);

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

            case systemWrapper : {
                return (T)new SystemWrapper();
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