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

import com.rf1m.image2css.Image2Css;
import com.rf1m.image2css.domain.BeanType;
import com.rf1m.image2css.domain.ObjectFactory;
import com.rf1m.image2css.exception.Image2CssException;

import java.io.PrintStream;

import static com.rf1m.image2css.ContentTemplates.ABOUT;
import static com.rf1m.image2css.cli.CommandLineParametersParser.HELP;

public class CommandLineRunner {
    final ObjectFactory objectFactory;

    public static void main(final String args[]) throws Exception {
        final ObjectFactory objectFactory = ObjectFactory.getInstance();
        final PrintStream printStream = objectFactory.instance(BeanType.defaultPrintStream);

        printStream.println(ABOUT);

        if(0 == args.length){
            new CommandLineRunner(objectFactory).showHelp(printStream);
        }else{
            new CommandLineRunner(objectFactory).execute(printStream, args);
        }
    }

    protected void showHelp(final PrintStream printStream) {
        printStream.println(HELP);
    }

    public CommandLineRunner(final ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
    }

    protected void execute(final PrintStream printStream, final String args[]) {
        final CommandLineParametersParser commandLineParametersParser = objectFactory.instance(BeanType.commandLineParametersParser);
        final Image2Css image2Css = objectFactory.instance(BeanType.image2css);

        runner(image2Css,  commandLineParametersParser, printStream, args);
    }

    protected void runner(final Image2Css image2Css, final CommandLineParametersParser commandLineParametersParser, final PrintStream printStream, final String[] args) {
        try{
            final Parameters parameters = commandLineParametersParser.parse(args);
            image2Css.execute(parameters);
        }catch(Image2CssException exception) {
            printStream.println(exception.getMessage());
            printStream.println(HELP);
        }catch(Exception e) {
            final String issueUrl = "https://github.com/mhuckaby/image2cssConverter/issues";
            final String messageTemplate = "Abnormal program exit. Please open an issue at %1$s\n";
            final String formattedMessage = String.format(messageTemplate, issueUrl);
            printStream.println(formattedMessage);
            e.printStackTrace();
        }
    }

}
