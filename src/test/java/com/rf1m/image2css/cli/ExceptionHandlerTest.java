package com.rf1m.image2css.cli;


import com.rf1m.image2css.exception.Image2CssException;
import com.rf1m.image2css.ioc.BeanType;
import com.rf1m.image2css.ioc.ObjectFactory;
import org.apache.commons.cli.ParseException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.PrintStream;
import java.util.ResourceBundle;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ExceptionHandlerTest {

    @Mock
    ObjectFactory objectFactory;

    @Mock
    PrintStream printStream;

    @Mock
    ResourceBundle resourceBundle;

    ExceptionHandler exceptionHandler;

    @Before
    public void before() {
        exceptionHandler = spy(new ExceptionHandler(objectFactory, printStream, resourceBundle));
    }

    @Test
    public void handleExceptionShouldPrintException() {
        final String issueUrl = "issueUrl";
        final String messageTemplate = "messageTemplate";
        final String formattedMessage = "formattedMessage";
        final String message = "message";

        Exception exception = mock(Exception.class);

        doReturn(issueUrl)
            .when(exceptionHandler)
            .getString("issue.url");

        doReturn(messageTemplate)
            .when(exceptionHandler)
            .getString("message.abnormal.exit");

        when(exception.getMessage())
            .thenReturn(message);

        doReturn(formattedMessage)
            .when(exceptionHandler)
            .format(messageTemplate, message, issueUrl);

        exceptionHandler.handleException(exception);

        verify(exceptionHandler, times(1))
            .getString("issue.url");

        verify(exceptionHandler, times(1))
            .getString("message.abnormal.exit");

        verify(exception, times(1))
            .getMessage();

        verify(exceptionHandler, times(1))
            .format(messageTemplate, message, issueUrl);

        verify(printStream, times(1))
            .println(formattedMessage);

        verify(exception, times(1))
            .printStackTrace();
    }

    @Test
    public void handleImage2CssExceptionShouldShowExceptionMessage() {
        final String exceptionFormat = "exceptionFormat";
        final String formattedExceptionMessage = "formattedExceptionMessage";
        final String exceptionMessage = "exceptionMessage";

        Image2CssException image2CssException = mock(Image2CssException.class);

        doReturn(exceptionFormat)
            .when(exceptionHandler)
            .getString("format.exception");

        when(image2CssException.getMessage())
            .thenReturn(exceptionMessage);

        doReturn(formattedExceptionMessage)
            .when(exceptionHandler)
            .format(exceptionFormat, exceptionMessage);

        exceptionHandler.handleImage2CssException(image2CssException);

        verify(exceptionHandler, times(1))
            .getString("format.exception");

        verify(image2CssException, times(1))
            .getMessage();

        verify(exceptionHandler, times(1))
            .format(exceptionFormat, exceptionMessage);

        verify(printStream, times(1))
            .println(anyString());
    }

    @Test
    public void handleParseExceptionShouldPrintFormattedErrorMessageAndShowHelp() {
        final String exceptionFormat = "exceptionFormat";
        final String formattedExceptionMessage = "formattedExceptionMessage";
        final String exceptionMessage = "exceptionMessage";

        Image2CssHelpFormatter image2CssHelpFormatter = mock(Image2CssHelpFormatter.class);
        ParseException parseException = mock(ParseException.class);

        when(objectFactory.instance(BeanType.helpFormatter))
            .thenReturn(image2CssHelpFormatter);

        doReturn(exceptionFormat)
            .when(exceptionHandler)
            .getString("format.exception");

        when(parseException.getMessage())
            .thenReturn(exceptionMessage);

        doReturn(formattedExceptionMessage)
            .when(exceptionHandler)
            .format(exceptionFormat, exceptionMessage);

        exceptionHandler.handleParseException(parseException);

        verify(objectFactory, times(1))
            .instance(BeanType.helpFormatter);

        verify(exceptionHandler)
            .getString("format.exception");

        verify(parseException, times(1))
            .getMessage();

        verify(printStream, times(1))
            .println(formattedExceptionMessage);

        verify(image2CssHelpFormatter, times(1))
            .showHelp();

        verify(exceptionHandler, times(1))
            .format(exceptionFormat, exceptionMessage);
    }

}
