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

import com.rf1m.image2css.exception.Image2CssValidationException;
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
