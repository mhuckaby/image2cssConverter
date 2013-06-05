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
package com.rf1m.image2css.cli;

import com.rf1m.image2css.domain.CssClass;
import com.rf1m.image2css.domain.SupportedImageType;
import com.rf1m.image2css.exception.Image2CssException;
import com.rf1m.image2css.ioc.BeanType;
import com.rf1m.image2css.ioc.ObjectFactory;
import com.rf1m.image2css.service.ImageConversionService;
import com.rf1m.image2css.util.file.FileUtils;
import org.apache.commons.cli.ParseException;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class CommandLineRunner {
    protected final ObjectFactory objectFactory;
    protected final PrintStream printStream;
    protected final ResourceBundle resourceBundle;
    protected final CommandLineRunnerValidator commandLineRunnerValidator;
    protected final CommandLineParametersParser commandLineParametersParser;
    protected final ExceptionHandler exceptionHandler;
    protected final FileUtils fileUtils;
    protected final ImageConversionService imageConversionService;
    protected final CommandLineRunnerOutputManager commandLineRunnerOutputManager;

    public CommandLineRunner(final ObjectFactory objectFactory, final PrintStream printStream,
                             final ResourceBundle resourceBundle, final CommandLineRunnerValidator commandLineRunnerValidator,
                             final CommandLineParametersParser commandLineParametersParser, final ExceptionHandler exceptionHandler,
                             final FileUtils fileUtils, final ImageConversionService imageConversionService,
                             final CommandLineRunnerOutputManager commandLineRunnerOutputManager) {

        this.objectFactory = objectFactory;
        this.printStream = printStream;
        this.resourceBundle = resourceBundle;
        this.commandLineRunnerValidator = commandLineRunnerValidator;
        this.commandLineParametersParser = commandLineParametersParser;
        this.exceptionHandler = exceptionHandler;
        this.fileUtils = fileUtils;
        this.imageConversionService = imageConversionService;
        this.commandLineRunnerOutputManager = commandLineRunnerOutputManager;
    }

    public static void main(final String[] arguments) throws Exception {
        final ObjectFactory objectFactory = ObjectFactory.getInstance();
        final CommandLineRunner commandLineRunner = objectFactory.instance(BeanType.commandLineRunner);
        commandLineRunner.run(arguments);
    }

    protected void run(final String[] arguments) {
        try {
            final Parameters parameters = this.initialize(arguments);
            this.execute(parameters);
        }catch(final ParseException parseException) {
            this.exceptionHandler.handleParseException(parseException);
        }catch(final Image2CssException image2CssException) {
            this.exceptionHandler.handleImage2CssException(image2CssException);
        }catch(final Exception e) {
            this.exceptionHandler.handleException(e);
        }
    }

    protected Parameters initialize(final String[] arguments) throws ParseException {
        this.commandLineRunnerOutputManager.showAbout();
        this.commandLineRunnerValidator.argumentLengthCheck(arguments);

        final Parameters parameters = this.commandLineParametersParser.parse(arguments);
        this.commandLineRunnerValidator.validateParameters(parameters);

        return parameters;
    }

    protected void execute(final Parameters parameters) throws IOException {
        final File targetImageFile = parameters.getImageFile();
        final Set<SupportedImageType> supportedImageTypes = parameters.getSupportedTypes();
        final File[] imageFiles = this.fileUtils.getImagesForConversion(targetImageFile, supportedImageTypes);
        final List<CssClass> cssEntries = this.objectFactory.instance(BeanType.arrayList);

        for(final File imageFile : imageFiles){
            final CssClass cssClass = this.imageConversionService.convert(imageFile);
            cssEntries.add(cssClass);
        }

        this.commandLineRunnerOutputManager.doOutput(parameters, cssEntries);
    }
}