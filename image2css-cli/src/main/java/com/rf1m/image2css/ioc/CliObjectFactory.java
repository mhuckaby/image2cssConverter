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

import com.rf1m.image2css.cli.*;
import com.rf1m.image2css.cmn.domain.SupportedImageType;
import com.rf1m.image2css.cmn.exception.Errors;
import com.rf1m.image2css.cmn.exception.Image2CssException;
import com.rf1m.image2css.cmn.ioc.AbstractFactory;
import com.rf1m.image2css.cmn.ioc.CommonObjectType;
import com.rf1m.image2css.cmn.ioc.CommonObjectFactory;
import com.rf1m.image2css.cmn.service.ImageConversionService;
import com.rf1m.image2css.cmn.util.file.FileUtils;
import com.rf1m.image2css.out.*;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import java.io.File;
import java.io.PrintStream;
import java.util.ResourceBundle;
import java.util.Set;


public class CliObjectFactory extends AbstractFactory<CliObjectType> {
    protected final String aboutProject = ResourceBundle.getBundle("image2css").getString("about.project");
    protected final String htmlIndexTemplate = ResourceBundle.getBundle("image2css").getString("template.html.index");
    protected final String htmlCssEntryTemplate = ResourceBundle.getBundle("image2css").getString("template.html.css.entry");


    public CliObjectFactory() {
        super(new CommonObjectFactory());
    }

    @Override
    protected Class<CliObjectType> instanceOfCatalogue() {
        return CliObjectType.class;
    }

    @Override
    protected <T> T createInstanceByFactory(CliObjectType beanType, Object... args) {
        switch(beanType) {
            case basicParser:
                return (T)new BasicParser();

            case commandLineParametersParser: {
                final BasicParser basicParser = this.getInstance(CliObjectType.basicParser);

                final Option optionCssFile = this.getInstance(CliObjectType.optionCssFile);
                final Option optionHtmlFile = this.getInstance(CliObjectType.optionHtmlFile);
                final Option optionImageFile = this.getInstance(CliObjectType.optionImageFile);
                final Option optionSupportedImageTypes = this.getInstance(CliObjectType.optionImageTypes);
                final Option optionSyso = this.getInstance(CliObjectType.optionSyso);

                final Options options = this.getInstance(CliObjectType.options);

                return (T)new CommandLineParametersParser(basicParser, optionCssFile, optionHtmlFile, optionImageFile, optionSupportedImageTypes,
                                optionSyso, options, this);
            }

            case commandLineRunner: {
                final PrintStream printStream = this.getInstance(CliObjectType.defaultPrintStream);
                final ResourceBundle resourceBundle = this.getInstance(CommonObjectType.resourceBundle);
                final CommandLineRunnerValidator commandLineRunnerValidator = this.getInstance(CliObjectType.commandLineRunnerValidator);
                final CommandLineParametersParser commandLineParametersParser = this.getInstance(CliObjectType.commandLineParametersParser);
                final ExceptionHandler exceptionHandler = this.getInstance(CliObjectType.exceptionHandler);
                final FileUtils fileUtils = this.getInstance(CommonObjectType.fileUtils);
                final ImageConversionService imageConversionService = this.getInstance(CommonObjectType.defaultImageConversionService);
                final CommandLineRunnerOutputManager commandLineRunnerOutputManager = this.getInstance(CliObjectType.commandLineRunnerOutputManager);
                final CommandLineRunner commandLineRunner =
                    new CommandLineRunner(this, printStream, resourceBundle, commandLineRunnerValidator,
                        commandLineParametersParser, exceptionHandler, fileUtils,
                        imageConversionService, commandLineRunnerOutputManager);

                return (T)commandLineRunner;
            }

            case commandLineRunnerOutputManager: {
                final Output consoleOutput = this.getInstance(CliObjectType.consoleOutput);
                final Output cssOutput = this.getInstance(CliObjectType.cssOutput);
                final Output htmlOutput = this.getInstance(CliObjectType.htmlOutput);
                final ReportOutput reportOutput = this.getInstance(CliObjectType.consoleOutput);
                final PrintStream printStream = this.getInstance(CliObjectType.defaultPrintStream);
                final CommandLineRunnerOutputManager commandLineRunnerOutputManager =
                        new CommandLineRunnerOutputManager(consoleOutput, cssOutput, htmlOutput, reportOutput, printStream, aboutProject);

                return (T) commandLineRunnerOutputManager;
            }

            case commandLineRunnerValidator: {
                final Image2CssHelpFormatter image2CssHelpFormatter = this.getInstance(CliObjectType.helpFormatter);
                final SystemWrapper systemWrapper = this.getInstance(CliObjectType.systemWrapper);
                final CommandLineRunnerValidator commandLineRunnerValidator = new CommandLineRunnerValidator(image2CssHelpFormatter, systemWrapper);

                return (T)commandLineRunnerValidator;
            }

            case consoleOutput:
                final ResourceBundle resourceBundle = this.getInstance(CommonObjectType.resourceBundle);
                return (T)new ConsoleOutput(resourceBundle, System.out);

            case cssOutput:
                return (T)new FileOutput(this);

            case defaultPrintStream:
                return (T) System.out;

            case exceptionHandler: {
                final Image2CssHelpFormatter image2CssHelpFormatter = this.getInstance(CliObjectType.helpFormatter);
                final PrintStream printStream = this.getInstance(CliObjectType.defaultPrintStream);
                final ResourceBundle objResourceBundle = this.getInstance(CommonObjectType.resourceBundle);
                final ExceptionHandler exceptionHandler = new ExceptionHandler(image2CssHelpFormatter, printStream, objResourceBundle);

                return (T)exceptionHandler;
            }

            case htmlOutput: {
                return (T)new HTMLOutput(this, htmlCssEntryTemplate, htmlIndexTemplate);
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
                final ResourceBundle objResourceBundle = this.getInstance(CommonObjectType.resourceBundle);
                final String command = objResourceBundle.getString("command.line.option.cmd.css.output.filename");
                final String description = objResourceBundle.getString("command.line.option.description.css.output.filename");

                final Option option = new Option(command, true, description);
                option.setRequired(true);

                return (T)option;
            }

            case optionHtmlFile: {
                final ResourceBundle objResourceBundle = this.getInstance(CommonObjectType.resourceBundle);
                final String command = objResourceBundle.getString("command.line.option.cmd.html.output.filename");
                final String description = objResourceBundle.getString("command.line.option.description.html.output.filename");

                final Option option = new Option(command, true, description);
                option.setRequired(true);

                return (T)option;
            }

            case optionImageFile: {
                final ResourceBundle objResourceBundle = this.getInstance(CommonObjectType.resourceBundle);
                final String command = objResourceBundle.getString("command.line.option.cmd.target.file.or.directory");
                final String description = objResourceBundle.getString("command.line.option.description.target.file.or.directory");

                final Option option = new Option(command, true, description);
                option.setRequired(true);
                option.setOptionalArg(false);

                return (T)option;
            }

            case optionImageTypes: {
                final ResourceBundle objResourceBundle = this.getInstance(CommonObjectType.resourceBundle);
                final String command = objResourceBundle.getString("command.line.option.cmd.included.image.types");
                final String description = objResourceBundle.getString("command.line.option.description.included.image.types");

                final Option option = new Option(command, true, description);
                option.setValueSeparator(' ');
                option.setArgs(3);

                return (T)option;
            }

            case optionSyso: {
                final ResourceBundle objResourceBundle = this.getInstance(CommonObjectType.resourceBundle);
                final String command = objResourceBundle.getString("command.line.option.cmd.output.to.screen");
                final String description = objResourceBundle.getString("command.line.option.description.output.to.screen");

                final Option option = new Option(command, false, description);

                return (T)option;
            }

            case options: {
                final Option cssFileOption = this.getInstance(CliObjectType.optionCssFile);
                final Option htmlFileOption = this.getInstance(CliObjectType.optionHtmlFile);
                final Option imageFileOption = this.getInstance(CliObjectType.optionImageFile);
                final Option imageTypesOption = this.getInstance(CliObjectType.optionImageTypes);
                final Option sysoOption = this.getInstance(CliObjectType.optionSyso);

                final Options options = new Options();

                options.addOption(cssFileOption);
                options.addOption(htmlFileOption);
                options.addOption(imageFileOption);
                options.addOption(imageTypesOption);
                options.addOption(sysoOption);

                return (T)options;
            }

            case helpFormatter: {
                final ResourceBundle objResourceBundle = this.getInstance(CommonObjectType.resourceBundle);
                final String helpText = objResourceBundle.getString("command.line.help.text");
                final Options image2cssOptions = this.getInstance(CliObjectType.options);
                final Image2CssHelpFormatter image2CssHelpFormatter = new Image2CssHelpFormatter(helpText, image2cssOptions);

                image2CssHelpFormatter.setWidth(85);
                return (T)image2CssHelpFormatter;
            }

            case reportOutput:
                return (T)(ReportOutput)this.getInstance(CliObjectType.consoleOutput);

            case systemWrapper : {
                return (T)new SystemWrapper();
            }

            default:
                throw new Image2CssException(Errors.parameterBeanType, beanType.name());
        }
    }
}
