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


public class CliObjectFactory extends AbstractFactory<CliBeanType> {
    protected final String aboutProject = ResourceBundle.getBundle("image2css").getString("about.project");
    protected final String htmlIndexTemplate = ResourceBundle.getBundle("image2css").getString("template.html.index");
    protected final String htmlCssEntryTemplate = ResourceBundle.getBundle("image2css").getString("template.html.css.entry");


    public CliObjectFactory() {
        super(new CommonObjectFactory());
    }

    @Override
    protected Class<CliBeanType> instanceOfCatalogue() {
        return CliBeanType.class;
    }

    @Override
    protected <T> T createInstanceByFactory(CliBeanType beanType, Object... args) {
        switch(beanType) {
            case basicParser:
                return (T)new BasicParser();

            case commandLineParametersParser: {
                final BasicParser basicParser = this.getInstance(CliBeanType.basicParser);

                final Option optionCssFile = this.getInstance(CliBeanType.optionCssFile);
                final Option optionHtmlFile = this.getInstance(CliBeanType.optionHtmlFile);
                final Option optionImageFile = this.getInstance(CliBeanType.optionImageFile);
                final Option optionSupportedImageTypes = this.getInstance(CliBeanType.optionImageTypes);
                final Option optionSyso = this.getInstance(CliBeanType.optionSyso);

                final Options options = this.getInstance(CliBeanType.options);

                return (T)new CommandLineParametersParser(basicParser, optionCssFile, optionHtmlFile, optionImageFile, optionSupportedImageTypes,
                                optionSyso, options, this);
            }

            case commandLineRunner: {
                final PrintStream printStream = this.getInstance(CliBeanType.defaultPrintStream);
                final ResourceBundle resourceBundle = this.getInstance(CommonObjectType.resourceBundle);
                final CommandLineRunnerValidator commandLineRunnerValidator = this.getInstance(CliBeanType.commandLineRunnerValidator);
                final CommandLineParametersParser commandLineParametersParser = this.getInstance(CliBeanType.commandLineParametersParser);
                final ExceptionHandler exceptionHandler = this.getInstance(CliBeanType.exceptionHandler);
                final FileUtils fileUtils = this.getInstance(CommonObjectType.fileUtils);
                final ImageConversionService imageConversionService = this.getInstance(CommonObjectType.defaultImageConversionService);
                final CommandLineRunnerOutputManager commandLineRunnerOutputManager = this.getInstance(CliBeanType.commandLineRunnerOutputManager);
                final CommandLineRunner commandLineRunner =
                    new CommandLineRunner(this, printStream, resourceBundle, commandLineRunnerValidator,
                        commandLineParametersParser, exceptionHandler, fileUtils,
                        imageConversionService, commandLineRunnerOutputManager);

                return (T)commandLineRunner;
            }

            case commandLineRunnerOutputManager: {
                final Output consoleOutput = this.getInstance(CliBeanType.consoleOutput);
                final Output cssOutput = this.getInstance(CliBeanType.cssOutput);
                final Output htmlOutput = this.getInstance(CliBeanType.htmlOutput);
                final ReportOutput reportOutput = this.getInstance(CliBeanType.consoleOutput);
                final PrintStream printStream = this.getInstance(CliBeanType.defaultPrintStream);
                final CommandLineRunnerOutputManager commandLineRunnerOutputManager =
                        new CommandLineRunnerOutputManager(consoleOutput, cssOutput, htmlOutput, reportOutput, printStream, aboutProject);

                return (T) commandLineRunnerOutputManager;
            }

            case commandLineRunnerValidator: {
                final Image2CssHelpFormatter image2CssHelpFormatter = this.getInstance(CliBeanType.helpFormatter);
                final SystemWrapper systemWrapper = this.getInstance(CliBeanType.systemWrapper);
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
                final Image2CssHelpFormatter image2CssHelpFormatter = this.getInstance(CliBeanType.helpFormatter);
                final PrintStream printStream = this.getInstance(CliBeanType.defaultPrintStream);
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
                final Option cssFileOption = this.getInstance(CliBeanType.optionCssFile);
                final Option htmlFileOption = this.getInstance(CliBeanType.optionHtmlFile);
                final Option imageFileOption = this.getInstance(CliBeanType.optionImageFile);
                final Option imageTypesOption = this.getInstance(CliBeanType.optionImageTypes);
                final Option sysoOption = this.getInstance(CliBeanType.optionSyso);

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
                final Options image2cssOptions = this.getInstance(CliBeanType.options);
                final Image2CssHelpFormatter image2CssHelpFormatter = new Image2CssHelpFormatter(helpText, image2cssOptions);

                image2CssHelpFormatter.setWidth(85);
                return (T)image2CssHelpFormatter;
            }

            case reportOutput:
                return (T)(ReportOutput)this.getInstance(CliBeanType.consoleOutput);

            case systemWrapper : {
                return (T)new SystemWrapper();
            }

            default:
                throw new Image2CssException(Errors.parameterBeanType, beanType.name());
        }
    }
}
