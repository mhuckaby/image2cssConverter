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
import java.util.List;
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

    Set<SupportedImageType> supportedImageTypes;

    CommandLineRunner commandLineRunner;

    @Before
    public void before() throws Exception {
        commandLineRunner = spy(new CommandLineRunner(objectFactory,
                                    commandLineRunnerValidator,
                                    commandLineParametersParser,
                                    exceptionHandler,
                                    fileUtils,
                                    imageConversionService,
                                    commandLineRunnerOutputManager,
                                    supportedImageTypes));

        when(commandLineParametersParser.parse(arguments))
            .thenReturn(parameters);
    }

    @Test
    public void handleLocalShouldParseAndConvert() throws Exception {
        final File targetImageFile = mock(File.class);
        final Set<SupportedImageType> supportedImageTypes = mock(Set.class);
        final File imageForConversion = mock(File.class);
        final File[] imagesForConversion = {imageForConversion};
        final List<CssClass> cssEntries = mock(List.class);
        final CssClass cssClass = mock(CssClass.class);

        when(parameters.getImageFile())
            .thenReturn(targetImageFile);

        when(parameters.getSupportedTypes())
            .thenReturn(supportedImageTypes);

        doReturn(imagesForConversion)
            .when(commandLineRunner)
            .getImagesForConversion(targetImageFile, supportedImageTypes);

        when(objectFactory.newMutableList())
            .thenReturn(cssEntries);

        when(imageConversionService.convert(imageForConversion))
            .thenReturn(cssClass);

        commandLineRunner.handleLocal(parameters);

        verify(parameters, times(1))
            .getImageFile();

        verify(parameters, times(1))
            .getSupportedTypes();

        verify(commandLineRunner, times(1))
            .getImagesForConversion(targetImageFile, supportedImageTypes);

        verify(objectFactory, times(1))
            .newMutableList();

        verify(imageConversionService, times(1))
            .convert(imageForConversion);

        verify(cssEntries, times(1))
            .add(cssClass);
    }

    @Test
    public void executeShouldDelegateToHandleLocalWhenIsLocalResourceParameterIsTrue() throws Exception {
        List<CssClass> returnValue = mock(List.class);

        when(parameters.isLocalResource())
            .thenReturn(true);

        doReturn(returnValue)
            .when(commandLineRunner)
            .handleLocal(parameters);

        commandLineRunner.execute(parameters);

        verify(parameters, times(1))
            .isLocalResource();

        verify(commandLineRunner, times(1))
            .handleLocal(parameters);

        verify(commandLineRunner, times(0))
            .handleRemote(parameters);
    }

    @Test
    public void executeShouldDelegateToHandleRemoteWhenIsLocalResourceParameterIsFalse() throws Exception {
        List<CssClass> returnValue = mock(List.class);

        when(parameters.isLocalResource())
            .thenReturn(false);

        doReturn(returnValue)
            .when(commandLineRunner)
            .handleLocal(parameters);

        commandLineRunner.execute(parameters);

        verify(parameters, times(1))
            .isLocalResource();

        verify(commandLineRunner, times(0))
            .handleLocal(parameters);

        verify(commandLineRunner, times(1))
            .handleRemote(parameters);
    }


    @Test
    public void runShouldInvokeParseExceptionHandlerWhenThatExceptionIsEncountered() throws Exception {
        final ParseException parseException = mock(ParseException.class);

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
        final Image2CssException image2CssException = mock(Image2CssException.class);

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
