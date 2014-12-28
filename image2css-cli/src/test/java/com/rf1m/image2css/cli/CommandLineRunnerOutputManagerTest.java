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

import com.rf1m.image2css.out.ConsoleOutput;
import com.rf1m.image2css.out.Output;
import com.rf1m.image2css.out.ReportOutput;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CommandLineRunnerOutputManagerTest {

    @Mock
    ConsoleOutput consoleOutput;

    @Mock
    Output cssOutput;

    @Mock
    Output htmlOutput;

    @Mock
    ReportOutput reportOutput;

    final String aboutProject = "aboutProject";

    CommandLineRunnerOutputManager commandLineRunnerOutputManager;

    @Before
    public void before() {
        commandLineRunnerOutputManager = spy(new CommandLineRunnerOutputManager(consoleOutput, cssOutput, htmlOutput, aboutProject));
    }

    @Test
    public void doOutputShouldNotProduceUndesiredOutput() throws Exception {
        final Parameters parameters = mock(Parameters.class);
        final List cssClasses = mock(List.class);

        when(parameters.isOutputToConsoleDesired())
            .thenReturn(false);

        when(parameters.isCssFileOutputDesired())
            .thenReturn(false);

        when(parameters.isHtmlFileOutputDesired())
            .thenReturn(false);

        commandLineRunnerOutputManager.doOutput(parameters, cssClasses);

        verify(parameters, times(2))
            .isOutputToConsoleDesired();

        verify(parameters, times(1))
            .isCssFileOutputDesired();

        verify(parameters, times(1))
            .isHtmlFileOutputDesired();

        verify(consoleOutput, times(0))
            .out(parameters, cssClasses);

        verify(consoleOutput, times(0))
            .generateReportOutput(parameters, cssClasses);

        verify(cssOutput, times(0))
            .out(parameters, cssClasses);

        verify(htmlOutput, times(0))
            .out(parameters, cssClasses);
    }

    @Test
    public void doOutputShouldProduceDesiredOutput() throws Exception {
        final Parameters parameters = mock(Parameters.class);
        final List cssClasses = mock(List.class);

        when(parameters.isOutputToConsoleDesired())
            .thenReturn(true);

        when(parameters.isCssFileOutputDesired())
            .thenReturn(true);

        when(parameters.isHtmlFileOutputDesired())
            .thenReturn(true);

        commandLineRunnerOutputManager.doOutput(parameters, cssClasses);

        verify(parameters, times(2))
            .isOutputToConsoleDesired();

        verify(parameters, times(1))
            .isCssFileOutputDesired();

        verify(parameters, times(1))
            .isHtmlFileOutputDesired();

        verify(consoleOutput, times(1))
            .out(parameters, cssClasses);

        verify(consoleOutput, times(1))
            .generateReportOutput(parameters, cssClasses);

        verify(cssOutput, times(1))
            .out(parameters, cssClasses);

        verify(htmlOutput, times(1))
            .out(parameters, cssClasses);
    }

    @Test
    public void showAboutShouldPrintAboutStringToPrintWriter() {
        commandLineRunnerOutputManager.showAbout();

        verify(commandLineRunnerOutputManager, times(1))
            .println(aboutProject);
    }


}
