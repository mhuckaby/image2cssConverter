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

import com.rf1m.image2css.out.Output;
import com.rf1m.image2css.out.ReportOutput;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.PrintStream;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CommandLineRunnerOutputManagerTest {

    @Mock
    Output consoleOutput;

    @Mock
    Output cssOutput;

    @Mock
    Output htmlOutput;

    @Mock
    ReportOutput reportOutput;

    @Mock
    PrintStream printStream;

    final String aboutProject = "aboutProject";

    CommandLineRunnerOutputManager commandLineRunnerOutputManager;

    @Before
    public void before() {
        commandLineRunnerOutputManager = spy(new CommandLineRunnerOutputManager(consoleOutput, cssOutput, htmlOutput, reportOutput, printStream, aboutProject));
    }

    @Test
    public void showAboutShouldPrintAboutStringToPrintWriter() {
        commandLineRunnerOutputManager.showAbout();

        verify(printStream, times(1))
            .println(anyString());
    }


}
