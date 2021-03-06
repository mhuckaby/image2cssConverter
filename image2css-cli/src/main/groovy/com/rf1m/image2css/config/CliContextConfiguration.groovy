/**
 *
 * Copyright (c) 2011-2016 Matthew D Huckaby. All rights reservered.
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
package com.rf1m.image2css.config

import com.rf1m.image2css.cli.CommandLineRunner
import com.rf1m.image2css.cli.CommandLineRunnerOutputManager
import com.rf1m.image2css.cli.CommandLineRunnerValidator
import com.rf1m.image2css.cli.ExceptionHandler
import com.rf1m.image2css.cli.Image2CssHelpFormatter
import com.rf1m.image2css.cli.Image2CssOption
import com.rf1m.image2css.cli.Image2CssOptions
import com.rf1m.image2css.cli.CommandLineParametersParser
import com.rf1m.image2css.out.ConsoleOutput
import com.rf1m.image2css.out.CssFileOutput
import com.rf1m.image2css.out.HtmlFileOutput
import com.rf1m.image2css.service.DefaultImageConversionService
import org.apache.commons.cli.BasicParser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment

import static com.rf1m.image2css.config.CommonContextConfiguration.defaultSupportedImageTypes

@Configuration
@Import(CommonContextConfiguration.class)
@PropertySource(value = "classpath:/image2css-cli.properties")
class CliContextConfiguration {

    @Autowired
    private Environment environment

    @Autowired
    DefaultImageConversionService defaultImageConversionService

    @Bean
    BasicParser basicParser() {
        return new BasicParser()
    }

    @Bean
    CommandLineParametersParser commandLineParametersParser() {
        return new CommandLineParametersParser(basicParser(), optionCssFile(), optionHtmlFile(), optionImageFile(),
                optionImageTypes(), optionSyso(), image2cssOptions())
    }

    @Bean
    Image2CssOption optionCssFile() {
        return new Image2CssOption("o", "CSS output filename", true, true)
    }

    @Bean
    Image2CssOption optionHtmlFile() {
        return new Image2CssOption("h", "HTML output filename", true, false)
    }

    @Bean
    Image2CssOption optionImageFile() {
        return new Image2CssOption("f", "Target image path, directory path, or URL", true, false)
    }

    @Bean
    Image2CssOption optionImageTypes() {
        return new Image2CssOption("i", "Space separated image types to include: png gif jpg", true, false, 3)
    }

    @Bean
    Image2CssOption optionSyso() {
        return new Image2CssOption("syso", "Output to screen", false, false)
    }

    @Bean
    Image2CssOptions image2cssOptions() {
        return new Image2CssOptions(optionCssFile(), optionHtmlFile(), optionImageFile(), optionImageTypes(), optionSyso())
    }

    @Bean
    Image2CssHelpFormatter helpFormatter() {
        return new Image2CssHelpFormatter(environment.getProperty("command.line.help.text"), image2cssOptions(), 85)
    }

    @Bean
    HtmlFileOutput htmlFileOutput() {
        return new HtmlFileOutput(environment.getProperty("template.html.css.entry"),
                environment.getProperty("template.html.index"))
    }

    @Bean
    CssFileOutput cssFileOutput() {
        return new CssFileOutput()
    }

    @Bean
    ConsoleOutput consoleOutput() {
        return new ConsoleOutput(environment.getProperty("message.generated.entry.count"),
                environment.getProperty("message.created.css.file"),
                environment.getProperty("message.created.html.file"))
    }

    @Bean
    ExceptionHandler exceptionHandler() {
        return new ExceptionHandler(helpFormatter(), environment.getProperty("message.abnormal.exit"), environment.getProperty("format.exception"))
    }

    // TODO verify about displays correctly
    @Bean
    CommandLineRunnerOutputManager commandLineRunnerOutputManager() {
        return new CommandLineRunnerOutputManager(consoleOutput(), cssFileOutput(), htmlFileOutput(),
                environment.getProperty("about.project"))
    }

    @Bean
    CommandLineRunnerValidator commandLineRunnerValidator() {
        return new CommandLineRunnerValidator(helpFormatter(), commandLineRunnerOutputManager())
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return new CommandLineRunner(commandLineRunnerValidator(), commandLineParametersParser(),
                exceptionHandler(), defaultImageConversionService, commandLineRunnerOutputManager(),
                defaultSupportedImageTypes)
    }
}
