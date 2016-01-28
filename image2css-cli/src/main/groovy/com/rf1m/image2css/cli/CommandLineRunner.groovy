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
import com.rf1m.image2css.exception.Image2CssException
import com.rf1m.image2css.service.ImageConversionService
import com.rf1m.image2css.io.ConversionFilenameFilter
//import org.apache.commons.cli.ParseException
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext


class CommandLineRunner {
//    protected final CommandLineRunnerValidator commandLineRunnerValidator
//    protected final CommandLineParametersParser commandLineParametersParser
//    protected final ExceptionHandler exceptionHandler
//    protected final ImageConversionService imageConversionService
//    protected final CommandLineRunnerOutputManager commandLineRunnerOutputManager
//    protected final Set<SupportedImageType> defaultSupportedImageTypes
//
//    public CommandLineRunner(final CommandLineRunnerValidator commandLineRunnerValidator,
//                             final CommandLineParametersParser commandLineParametersParser,
//                             final ExceptionHandler exceptionHandler,
//                             final ImageConversionService imageConversionService,
//                             final CommandLineRunnerOutputManager commandLineRunnerOutputManager,
//                             final Set<SupportedImageType> defaultSupportImageTypes) {
//
//        this.commandLineRunnerValidator = commandLineRunnerValidator
//        this.commandLineParametersParser = commandLineParametersParser
//        this.exceptionHandler = exceptionHandler
//        this.imageConversionService = imageConversionService
//        this.commandLineRunnerOutputManager = commandLineRunnerOutputManager
//        this.defaultSupportedImageTypes = defaultSupportImageTypes
//    }
//
//    public static void main(final String[] arguments) throws Exception {
//        ApplicationContext applicationContext =
//            new AnnotationConfigApplicationContext(CliContextConfiguration.class)
//        CommandLineRunner commandLineRunner = (CommandLineRunner)applicationContext.getBean("commandLineRunner")
//
//        commandLineRunner.run(arguments)
//    }
//
//    protected void run(final String[] arguments) {
//        try {
//            execute(initialize(arguments))
//        }catch(ParseException parseException) {
//            exceptionHandler.handleParseException(parseException)
//        }catch(Image2CssException image2CssException) {
//            exceptionHandler.handleImage2CssException(image2CssException)
//        }catch(Exception e) {
//            exceptionHandler.handleException(e)
//        }
//    }
//
//    protected Parameters initialize(final String[] arguments) throws ParseException {
//        commandLineRunnerValidator.argumentLengthCheck(arguments)
//        Parameters parameters = commandLineParametersParser.parse(arguments)
//        commandLineRunnerValidator.validateParameters(parameters)
//
//        parameters
//    }
//
//    protected void execute(final Parameters parameters) throws IOException {
//        List<CssClass> cssEntries = parameters.isLocalResource() ?
//            handleLocal(parameters) : handleRemote(parameters)
//        commandLineRunnerOutputManager.doOutput(parameters, cssEntries)
//    }
//
//    protected List<CssClass> handleRemote(final Parameters parameters) {
//        List<CssClass> cssEntries = new ArrayList(1)
//        CssClass cssClass = imageConversionService.convert(parameters.getURL())
//
//        cssEntries.add(cssClass)
//
//        cssEntries
//    }
//
//    protected List<CssClass> handleLocal(final Parameters parameters) throws IOException {
//        Set<SupportedImageType> supportedImageTypes = parameters.supportedTypes
//        File[] imageFiles = getImagesForConversion(parameters.imageFile, supportedImageTypes)
//        List<CssClass> cssEntries = new LinkedList()
//
//        for(File imageFile : imageFiles){
//            cssEntries.add(imageConversionService.convert(imageFile))
//        }
//
//        cssEntries
//    }
//
//    public File[] getImagesForConversion(final File imageFile, final Set<SupportedImageType> supportedTypes) throws Image2CssException {
//        if(imageFile.directory){
//            Set<SupportedImageType> filterFor =
//                supportedTypes.empty ? defaultSupportedImageTypes : supportedTypes
//            imageFile.listFiles(new ConversionFilenameFilter(filterFor))
//        }else{
//            [imageFile]
//        }
//    }

}