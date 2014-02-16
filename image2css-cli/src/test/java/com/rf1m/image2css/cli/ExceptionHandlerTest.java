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


import com.rf1m.image2css.exception.Image2CssException;
import org.apache.commons.cli.ParseException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ResourceBundle;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExceptionHandlerTest {

    final String issueUrl = "issueUrl";
    final String abnormalExitTemplate = "abnormalExitTemplate";
    final String exceptionMessageTemplate = "exceptionMessageTemplate";

    @Mock
    Image2CssHelpFormatter helpFormatter;

    @Mock
    ResourceBundle resourceBundle;

    ExceptionHandler exceptionHandler;

    @Before
    public void before() {
        exceptionHandler = spy(new ExceptionHandler(helpFormatter, issueUrl, abnormalExitTemplate, exceptionMessageTemplate));

        doNothing()
            .when(exceptionHandler)
            .println(anyString());
    }

    @Test
    public void handleExceptionShouldPrintException() {
        final String formattedMessage = "formattedMessage";
        final String message = "message";

        final Exception exception = mock(Exception.class);

        when(exception.getMessage())
            .thenReturn(message);

        doReturn(formattedMessage)
            .when(exceptionHandler)
            .format(abnormalExitTemplate, message, issueUrl);

        exceptionHandler.handleException(exception);

        verify(exception, times(1))
            .getMessage();

        verify(exceptionHandler, times(1))
            .format(abnormalExitTemplate, message, issueUrl);

        verify(exceptionHandler, times(1))
            .println(formattedMessage);

        verify(exception, times(1))
            .printStackTrace();
    }

    @Test
    public void handleImage2CssExceptionShouldShowExceptionMessage() {
        final String formattedExceptionMessage = "formattedExceptionMessage";
        final String exceptionMessage = "exceptionMessage";

        final Image2CssException image2CssException = mock(Image2CssException.class);

        when(image2CssException.getMessage())
            .thenReturn(exceptionMessage);

        doReturn(formattedExceptionMessage)
            .when(exceptionHandler)
            .format(exceptionMessageTemplate, exceptionMessage);

        exceptionHandler.handleImage2CssException(image2CssException);

        verify(image2CssException, times(1))
            .getMessage();

        verify(exceptionHandler, times(1))
            .format(exceptionMessageTemplate, exceptionMessage);

        verify(exceptionHandler, times(1))
            .println(anyString());
    }

    @Test
    public void handleParseExceptionShouldPrintFormattedErrorMessageAndShowHelp() {
        final String formattedExceptionMessage = "formattedExceptionMessage";
        final String exceptionMessage = "exceptionMessage";

        final ParseException parseException = mock(ParseException.class);

        when(parseException.getMessage())
            .thenReturn(exceptionMessage);

        doReturn(formattedExceptionMessage)
            .when(exceptionHandler)
            .format(exceptionMessageTemplate, exceptionMessage);

        exceptionHandler.handleParseException(parseException);

        verify(parseException, times(1))
            .getMessage();

        verify(exceptionHandler, times(1))
            .println(formattedExceptionMessage);

        verify(helpFormatter, times(1))
            .showHelp();

        verify(exceptionHandler, times(1))
            .format(exceptionMessageTemplate, exceptionMessage);
    }

}
