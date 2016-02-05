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
package com.rf1m.image2css.cli

import com.rf1m.image2css.config.CliContextConfiguration
import com.rf1m.image2css.domain.CssClass
import com.rf1m.image2css.domain.SupportedImageType
import com.rf1m.image2css.service.ImageConversionService
import org.kohsuke.args4j.CmdLineException
import org.kohsuke.args4j.CmdLineParser
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext

class CommandLineRunner {

    public static void main(final String[] args) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("image2css-cli")
        int exit = 0

        try {
            CommandLineRunner.init(args).execute()
        }catch(CmdLineException cmdLineException) {
            println cmdLineException.message
            exit = -1
        }catch(Exception e) {
            // TODO Print full stacktrace to a tmp file for submission
            e.printStackTrace()
            String message = resourceBundle.getString("message.abnormal.exit")
            println String.format(message, e.message)
            exit = -1
        }

        System.exit(exit)
    }

    private static CommandLineRunner init(final String[] args) {
        CommandLineArgument commandLineArgument = new CommandLineArgument(originalArgs: args)
        commandLineArgument.cmdLineParser = new CmdLineParser(commandLineArgument)
        commandLineArgument.cmdLineParser.parseArgument(args)

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(CliContextConfiguration.class)
        ImageConversionService conversionService = applicationContext.getBean(ImageConversionService.class)
        CommandLineRunnerOutputManager commandLineRunnerOutputManager = applicationContext.getBean(CommandLineRunnerOutputManager.class)

        new CommandLineRunner(commandLineArgument: commandLineArgument, conversionService: conversionService, commandLineRunnerOutputManager: commandLineRunnerOutputManager)
    }

    CommandLineArgument commandLineArgument
    ImageConversionService conversionService
    CommandLineRunnerOutputManager commandLineRunnerOutputManager

    private void execute() {
        if(commandLineArgument.version) {
            commandLineRunnerOutputManager.showAbout()
            return
        }else if(commandLineArgument.help) {
            commandLineArgument.cmdLineParser.printUsage(System.out)
            return
        }

        List<CssClass> cssClasses = []

        if(commandLineArgument.filename ==~ '^http[s]?://.*?') {
            cssClasses.add(conversionService.convert(commandLineArgument.filename))
        }else {
            Collection<SupportedImageType> include = (commandLineArgument.includes as List).collect({
                SupportedImageType.valueOf(it)
            })
            File file = new File(commandLineArgument.filename)
            cssClasses.addAll(conversionService.convert(include, file))
        }

        commandLineRunnerOutputManager.doOutput(commandLineArgument, cssClasses)
    }

}
