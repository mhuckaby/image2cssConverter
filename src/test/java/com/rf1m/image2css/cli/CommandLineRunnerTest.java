package com.rf1m.image2css.cli;

import com.rf1m.image2css.Image2Css;
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

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CommandLineRunnerTest {

    final String argument = "argument";

    final String[] arguments = {argument};

    @Mock
    ObjectFactory objectFactory;

    @Mock
    Image2CssHelpFormatter image2CssHelpFormatter;

    @Mock
    SystemWrapper systemWrapper;

    @Mock
    PrintStream printStream;

    @Mock
    Image2Css image2Css;

    @Mock
    CommandLineParametersParser commandLineParametersParser;

    @Mock
    Parameters parameters;

    ResourceBundle resourceBundle;
    CommandLineRunner commandLineRunner;

    @Before
    public void before() throws Exception {
        resourceBundle = ResourceBundle.getBundle("image2css");
        commandLineRunner = spy(new CommandLineRunner(objectFactory));

        when(objectFactory.instance(BeanType.resourceBundle))
            .thenReturn(resourceBundle);

        when(objectFactory.instance(BeanType.helpFormatter))
            .thenReturn(image2CssHelpFormatter);

        when(objectFactory.instance(BeanType.systemWrapper))
            .thenReturn(systemWrapper);

        when(objectFactory.instance(BeanType.defaultPrintStream))
            .thenReturn(printStream);

        when(objectFactory.instance(BeanType.image2css))
            .thenReturn(image2Css);

        when(objectFactory.instance(BeanType.commandLineParametersParser))
            .thenReturn(commandLineParametersParser);

        when(commandLineParametersParser.parse(arguments))
            .thenReturn(parameters);
    }

    @Test
    public void executeShouldParseAndConvert() throws Exception {
        doNothing()
            .when(commandLineRunner)
            .showAbout(resourceBundle, printStream);

        doNothing()
            .when(commandLineRunner)
            .argumentLengthCheck(arguments);

        commandLineRunner.execute(arguments);

        verify(image2Css, times(1))
            .execute(parameters);

        verify(commandLineRunner, times(1))
            .showAbout(resourceBundle, printStream);

        verify(commandLineRunner, times(1))
            .argumentLengthCheck(arguments);

        verify(objectFactory, times(1))
            .instance(BeanType.image2css);

        verify(objectFactory, times(1))
            .instance(BeanType.commandLineParametersParser);

        verify(commandLineParametersParser, times(1))
            .parse(arguments);
    }

    @Test
    public void executeShouldInvokeParseExceptionHandlerWhenThatExceptionIsEncountered() throws Exception {
        doNothing()
            .when(commandLineRunner)
            .showAbout(resourceBundle, printStream);

        doNothing()
            .when(commandLineRunner)
            .argumentLengthCheck(arguments);

        ParseException parseException = mock(ParseException.class);

        doThrow(parseException)
            .when(commandLineParametersParser)
            .parse(arguments);

        doNothing()
            .when(commandLineRunner)
            .handleParseException(resourceBundle, printStream, parseException);

        commandLineRunner.execute(arguments);

        verify(commandLineRunner, times(1))
            .handleParseException(resourceBundle, printStream, parseException);
    }

    @Test
    public void executeShouldInvokeImage2CssExceptionHandlerWhenThatExceptionIsEncountered() throws Exception {
        doNothing()
            .when(commandLineRunner)
            .showAbout(resourceBundle, printStream);

        doNothing()
            .when(commandLineRunner)
            .argumentLengthCheck(arguments);

        Image2CssException image2CssException = mock(Image2CssException.class);

        doThrow(image2CssException)
            .when(image2Css)
            .execute(parameters);

        doNothing()
            .when(commandLineRunner)
            .handleImage2CssException(resourceBundle, printStream, image2CssException);

        commandLineRunner.execute(arguments);

        verify(commandLineRunner, times(1))
            .handleImage2CssException(resourceBundle, printStream, image2CssException);
    }

    @Test
    public void executeShouldInvokeExceptionHandlerWhenThatExceptionIsEncountered() throws Exception {
        doNothing()
            .when(commandLineRunner)
            .showAbout(resourceBundle, printStream);

        doNothing()
            .when(commandLineRunner)
            .argumentLengthCheck(arguments);

        RuntimeException exception = mock(RuntimeException.class);

        doThrow(exception)
            .when(image2Css)
            .execute(parameters);

        doNothing()
            .when(commandLineRunner)
            .handleException(resourceBundle, printStream, exception);

        commandLineRunner.execute(arguments);

        verify(commandLineRunner, times(1))
            .handleException(resourceBundle, printStream, exception);
    }

    @Test
    public void argumentLengthCheckShouldDoNothingIfArgumentLengthIsGtZero() {
        commandLineRunner.argumentLengthCheck(arguments);

        verify(objectFactory, times(0))
            .instance(BeanType.helpFormatter);

        verify(objectFactory, times(0))
            .instance(BeanType.systemWrapper);
    }

    @Test
    public void argumentLengthCheckShouldShowHelpAndExitIfArgumentLengthIsEqualToZero() {
        commandLineRunner.argumentLengthCheck(new String[] {});

        verify(objectFactory, times(1))
            .instance(BeanType.helpFormatter);

        verify(objectFactory, times(1))
            .instance(BeanType.systemWrapper);

        verify(image2CssHelpFormatter, times(1))
            .showHelp();

        verify(systemWrapper, times(1))
            .exit();
    }

    @Test
    public void showAboutShouldPrintAboutStringToPrintWriter() {
        commandLineRunner.showAbout(resourceBundle, printStream);

        verify(printStream, times(1))
            .println(anyString());
    }

    @Test
    public void handleParseExceptionShouldPrintFormattedErrorMessageAndShowHelp() {
        ParseException parseException = mock(ParseException.class);

        commandLineRunner.handleParseException(resourceBundle, printStream, parseException);

        verify(printStream, times(1))
            .println(anyString());

        verify(image2CssHelpFormatter, times(1))
            .showHelp();
    }

    @Test
    public void handleImage2CssExceptionShouldShowExceptionMessage() {
        Image2CssException image2CssException = mock(Image2CssException.class);

        commandLineRunner.handleImage2CssException(resourceBundle, printStream, image2CssException);

        verify(printStream, times(1))
            .println(anyString());
    }

    @Test
    public void handleExceptionShouldPrintException() {
        final String message = "message";

        Exception exception = mock(Exception.class);

        when(exception.getMessage())
            .thenReturn(message);

        commandLineRunner.handleException(resourceBundle, printStream, exception);

        verify(printStream, times(1))
            .println(anyString());

        verify(exception, times(1))
            .printStackTrace();
    }

}
