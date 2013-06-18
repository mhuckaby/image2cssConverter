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

import com.rf1m.image2css.cmn.domain.CssClass;
import com.rf1m.image2css.cmn.domain.SupportedImageType;
import com.rf1m.image2css.cmn.exception.Image2CssException;
import com.rf1m.image2css.cmn.ioc.CommonObjectType;
import com.rf1m.image2css.cmn.service.ImageConversionService;
import com.rf1m.image2css.cmn.util.file.FileUtils;
import com.rf1m.image2css.ioc.CliObjectFactory;
import org.apache.commons.cli.ParseException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.PrintStream;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CommandLineRunnerTest {

    final String argument = "argument";

    final String[] arguments = {argument};

    @Mock
    CliObjectFactory objectFactory;

    @Mock
    Image2CssHelpFormatter image2CssHelpFormatter;

    @Mock
    SystemWrapper systemWrapper;

    @Mock
    PrintStream printStream;

    @Mock
    CommandLineParametersParser commandLineParametersParser;

    @Mock
    CommandLineRunnerValidator commandLineRunnerValidator;

    @Mock
    ExceptionHandler exceptionHandler;

    @Mock
    Parameters parameters;

    @Mock
    FileUtils fileUtils;

    @Mock
    ImageConversionService imageConversionService;

    @Mock
    CommandLineRunnerOutputManager commandLineRunnerOutputManager;

    ResourceBundle resourceBundle;
    CommandLineRunner commandLineRunner;

    @Before
    public void before() throws Exception {
        resourceBundle = ResourceBundle.getBundle("image2css");
        commandLineRunner = spy(
            new CommandLineRunner(objectFactory, printStream, resourceBundle, commandLineRunnerValidator,
                commandLineParametersParser, exceptionHandler, fileUtils, imageConversionService, commandLineRunnerOutputManager)
        );

        when(commandLineParametersParser.parse(arguments))
            .thenReturn(parameters);
    }

    @Test
    public void executeShouldParseAndConvert() throws Exception {
        File targetImageFile = mock(File.class);
        Set<SupportedImageType> supportedImageTypes = mock(Set.class);
        File imageForConversion = mock(File.class);
        File[] imagesForConversion = {imageForConversion};
        List<CssClass> cssEntries = mock(List.class);
        CssClass cssClass = mock(CssClass.class);

        when(parameters.getImageFile())
            .thenReturn(targetImageFile);

        when(parameters.getSupportedTypes())
            .thenReturn(supportedImageTypes);

        when(fileUtils.getImagesForConversion(targetImageFile, supportedImageTypes))
            .thenReturn(imagesForConversion);

        when(objectFactory.getInstance(CommonObjectType.arrayList))
            .thenReturn(cssEntries);

        when(imageConversionService.convert(imageForConversion))
            .thenReturn(cssClass);

        commandLineRunner.execute(parameters);

        verify(parameters, times(1))
            .getImageFile();

        verify(parameters, times(1))
            .getSupportedTypes();

        verify(fileUtils, times(1))
            .getImagesForConversion(targetImageFile, supportedImageTypes);

        verify(objectFactory, times(1))
            .getInstance(CommonObjectType.arrayList);

        verify(imageConversionService, times(1))
            .convert(imageForConversion);

        verify(cssEntries, times(1))
            .add(cssClass);
    }

    @Test
    public void runShouldInvokeParseExceptionHandlerWhenThatExceptionIsEncountered() throws Exception {
        ParseException parseException = mock(ParseException.class);

        doThrow(parseException)
            .when(commandLineRunner)
            .initialize(arguments);

        doNothing()
            .when(exceptionHandler)
            .handleParseException(parseException);

        commandLineRunner.run(arguments);

        verify(exceptionHandler, times(1))
            .handleParseException(parseException);
    }

    @Test
    public void executeShouldInvokeImage2CssExceptionHandlerWhenImage2CssExceptionExceptionIsEncountered() throws Exception {
        Image2CssException image2CssException = mock(Image2CssException.class);

        doNothing()
            .when(commandLineRunnerOutputManager)
            .showAbout();

        doNothing()
            .when(commandLineRunnerValidator)
            .argumentLengthCheck(arguments);

        doThrow(image2CssException)
            .when(commandLineRunner)
            .execute(parameters);

        doNothing()
            .when(exceptionHandler)
            .handleImage2CssException(image2CssException);

        commandLineRunner.run(arguments);

        verify(exceptionHandler, times(1))
            .handleImage2CssException(image2CssException);
    }

    @Test
    public void executeShouldInvokeExceptionHandlerWhenThatExceptionIsEncountered() throws Exception {
        doNothing()
            .when(commandLineRunnerOutputManager)
            .showAbout();

        doNothing()
            .when(commandLineRunnerValidator)
            .argumentLengthCheck(arguments);

        RuntimeException exception = mock(RuntimeException.class);

        doThrow(exception)
            .when(commandLineRunner)
            .execute(parameters);

        doNothing()
            .when(exceptionHandler)
            .handleException(exception);

        commandLineRunner.run(arguments);

        verify(exceptionHandler, times(1))
            .handleException(exception);
    }

}
