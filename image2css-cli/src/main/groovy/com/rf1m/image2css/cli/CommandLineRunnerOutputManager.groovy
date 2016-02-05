/**
 *
 * Copyright (c) 2011-2016 Matthew D Huckaby. All rights reservered.
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
package com.rf1m.image2css.cli

import com.rf1m.image2css.domain.CssClass
import com.rf1m.image2css.out.ConsoleOutput
import com.rf1m.image2css.out.Output
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component


@Component
class CommandLineRunnerOutputManager {

    @Autowired
    private ConsoleOutput consoleOutput

    @Autowired
    @Qualifier("cssOutput")
    private Output cssOutput

    @Autowired
    @Qualifier("htmlOutput")
    private Output htmlOutput

    @Value('${about.project}')
    private String aboutProject

    protected PrintStream defaultOut = System.out

    protected void doOutput(final CommandLineArgument commandLineArgument, final List<CssClass> cssClasses) throws IOException {
        if(!cssClasses) {
            return
        }

        if(commandLineArgument.syso) {
            consoleOutput.out(commandLineArgument, cssClasses)
        }

        if(commandLineArgument.cssFile) {
            cssOutput.out(commandLineArgument, cssClasses)
        }

        if(commandLineArgument.htmlFile) {
            htmlOutput.out(commandLineArgument, cssClasses)
        }
    }

    protected void showAbout() {
        defaultOut.println(aboutProject)
    }
}
