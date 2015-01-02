package com.rf1m.image2css;

import com.rf1m.image2css.cli.CommandLineParametersParser;
import com.rf1m.image2css.cli.CommandLineRunner;
import com.rf1m.image2css.cli.CommandLineRunnerOutputManager;
import com.rf1m.image2css.cli.CommandLineRunnerValidator;
import com.rf1m.image2css.cli.ExceptionHandler;
import com.rf1m.image2css.cli.Image2CssHelpFormatter;
import com.rf1m.image2css.cli.Image2CssOption;
import com.rf1m.image2css.cli.Image2CssOptions;
import com.rf1m.image2css.cli.SystemWrapper;
import com.rf1m.image2css.config.Image2CssCommonContextConfiguration;
import com.rf1m.image2css.ioc.CliObjectFactory;
import com.rf1m.image2css.out.ConsoleOutput;
import com.rf1m.image2css.out.CssFileOutput;
import com.rf1m.image2css.out.HtmlFileOutput;
import com.rf1m.image2css.service.DefaultImageConversionService;
import org.apache.commons.cli.BasicParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import static com.rf1m.image2css.config.Image2CssCommonContextConfiguration.defaultSupportedImageTypes;

@Configuration
@Import(Image2CssCommonContextConfiguration.class)
@PropertySource(value = {"classpath:/image2css-cli.properties"})
public class Image2CssCliContextConfiguration {

    @Autowired
    private Environment environment;

    @Autowired
    DefaultImageConversionService defaultImageConversionService;

    @Bean
    public CliObjectFactory cliObjectFactory() {
        return new CliObjectFactory();
    }

    @Bean
    public BasicParser basicParser() {
        return new BasicParser();
    }

    @Bean
    public CommandLineParametersParser commandLineParametersParser() {
        return new CommandLineParametersParser(basicParser(), optionCssFile(), optionHtmlFile(), optionImageFile(),
                optionImageTypes(), optionSyso(), image2cssOptions(), cliObjectFactory());
    }

    @Bean
    public Image2CssOption optionCssFile() {
        return new Image2CssOption("o", "CSS output filename", true, true);
    }

    @Bean
    public Image2CssOption optionHtmlFile() {
        return new Image2CssOption("h", "HTML output filename", true, false);
    }

    @Bean
    public Image2CssOption optionImageFile() {
        return new Image2CssOption("f", "Target image or directory", true, false);
    }

    @Bean
    public Image2CssOption optionImageTypes() {
        return new Image2CssOption("i", "Space separated image types to include: png gif jpg", true, false, ' ', 3);
    }

    @Bean
    public Image2CssOption optionSyso() {
        return new Image2CssOption("syso", "Output to screen", false, false);
    }

    @Bean
    public Image2CssOptions image2cssOptions() {
        return new Image2CssOptions(optionCssFile(), optionHtmlFile(), optionImageFile(), optionImageTypes(), optionSyso());
    }

    @Bean
    public Image2CssHelpFormatter helpFormatter() {
        return new Image2CssHelpFormatter(environment.getProperty("command.line.help.text"), image2cssOptions(), 85);
    }

    @Bean
    public HtmlFileOutput htmlFileOutput() {
        return new HtmlFileOutput(cliObjectFactory(), environment.getProperty("template.html.css.entry"),
                environment.getProperty("template.html.index"));
    }

    @Bean
    public CssFileOutput cssFileOutput() {
        return new CssFileOutput(cliObjectFactory());
    }

    @Bean
    public ConsoleOutput consoleOutput() {
        return new ConsoleOutput(environment.getProperty("message.generated.entry.count"),
                environment.getProperty("message.created.css.file"),
                environment.getProperty("message.created.html.file"));
    }

    @Bean
    public ExceptionHandler exceptionHandler() {
        return new ExceptionHandler(helpFormatter(), environment.getProperty("issue.url"),
                environment.getProperty("message.abnormal.exit"), environment.getProperty("format.exception"));
    }

    @Bean
    public SystemWrapper systemWrapper() {
        return new SystemWrapper();
    }

    // TODO verify about displays correctly
    @Bean
    public CommandLineRunnerOutputManager commandLineRunnerOutputManager() {
        return new CommandLineRunnerOutputManager(consoleOutput(), cssFileOutput(), htmlFileOutput(),
                environment.getProperty("about.project"));
    }

    @Bean
    public CommandLineRunnerValidator commandLineRunnerValidator() {
        return new CommandLineRunnerValidator(helpFormatter(), commandLineRunnerOutputManager(), systemWrapper());
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return new CommandLineRunner(cliObjectFactory(), commandLineRunnerValidator(), commandLineParametersParser(),
                exceptionHandler(), defaultImageConversionService, commandLineRunnerOutputManager(),
                defaultSupportedImageTypes);
    }
}
