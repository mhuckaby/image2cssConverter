package com.rf1m.image2css.cli;

import com.rf1m.image2css.cmn.exception.Image2CssValidationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CommandLineRunnerValidatorTest {

    @Mock
    Image2CssHelpFormatter image2CssHelpFormatter;

    @Mock
    SystemWrapper systemWrapper;

    CommandLineRunnerValidator commandLineRunnerValidator;

    @Before
    public void before() {
        commandLineRunnerValidator = new CommandLineRunnerValidator(image2CssHelpFormatter, systemWrapper);
    }

    @Test
    public void argumentLengthCheckShouldDoNothingIfArgumentLengthIsGtZero() {
        final String[] arguments = {"argument"};

        commandLineRunnerValidator.argumentLengthCheck(arguments);

        verify(image2CssHelpFormatter, times(0))
            .showHelp();

        verify(systemWrapper, times(0))
            .exit();
    }

    @Test
    public void argumentLengthCheckShouldShowHelpAndExitIfArgumentLengthIsEqualToZero() {
        final String[] arguments = {};

        commandLineRunnerValidator.argumentLengthCheck(arguments);

        verify(image2CssHelpFormatter, times(1))
            .showHelp();

        verify(systemWrapper, times(1))
            .exit();
    }

    @Test(expected = Image2CssValidationException.class)
    public void validateParametersShouldThrowIfParametersIsNull() {
        commandLineRunnerValidator.validateParameters(null);
    }

}
