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
import com.rf1m.image2css.exception.Image2CssException;
import com.rf1m.image2css.ioc.BeanType;
import com.rf1m.image2css.ioc.ObjectFactory;
import org.apache.commons.cli.ParseException;

import java.io.PrintStream;
import java.util.ResourceBundle;

import static java.lang.String.format;

public class CommandLineRunner {
    protected final ObjectFactory objectFactory;

    public CommandLineRunner(final ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
    }

    public static void main(final String arguments[]) throws Exception {
        final ObjectFactory objectFactory = ObjectFactory.getInstance();
        final CommandLineRunner commandLineRunner = objectFactory.instance(BeanType.commandLineRunner);

        commandLineRunner.execute(arguments);
    }

    protected void execute(final String arguments[]) {
        final PrintStream printStream = this.objectFactory.instance(BeanType.defaultPrintStream);
        final ResourceBundle resourceBundle = this.objectFactory.instance(BeanType.resourceBundle);

        this.showAbout(resourceBundle, printStream);
        this.argumentLengthCheck(arguments);

        final Image2Css image2Css = this.objectFactory.instance(BeanType.image2css);
        final CommandLineParametersParser commandLineParametersParser =
            this.objectFactory.instance(BeanType.commandLineParametersParser);

        try {
            final Parameters parameters = commandLineParametersParser.parse(arguments);
            image2Css.execute(parameters);
        }catch(final ParseException parseException) {
            this.handleParseException(resourceBundle, printStream, parseException);
        }catch(final Image2CssException image2CssException) {
            this.handleImage2CssException(resourceBundle, printStream, image2CssException);
        }catch(final Exception e) {
            this.handleException(resourceBundle, printStream, e);
        }
    }

    protected void handleException(final ResourceBundle resourceBundle, final PrintStream printStream, final Exception e) {
        final String issueUrl = resourceBundle.getString("issue.url");
        final String messageTemplate = resourceBundle.getString("message.abnormal.exit");
        final String formattedMessage = format(messageTemplate, e.getMessage(), issueUrl);

        printStream.println(formattedMessage);
        e.printStackTrace();
    }

    protected void handleImage2CssException(final ResourceBundle resourceBundle, final PrintStream printStream, final Image2CssException image2CssException) {
        final String exceptionFormat = resourceBundle.getString("format.exception");
        final String formattedExceptionMessage = format(exceptionFormat, image2CssException.getMessage());

        printStream.println(formattedExceptionMessage);
    }

    protected void handleParseException(final ResourceBundle resourceBundle, final PrintStream printStream, final ParseException parseException) {
        final Image2CssHelpFormatter image2CssHelpFormatter = this.objectFactory.instance(BeanType.helpFormatter);
        final String exceptionFormat = resourceBundle.getString("format.exception");
        final String formattedExceptionMessage = format(exceptionFormat, parseException.getMessage());

        printStream.println(formattedExceptionMessage);
        image2CssHelpFormatter.showHelp();
    }

    protected void showAbout(final ResourceBundle resourceBundle, final PrintStream printStream) {
        final String about = resourceBundle.getString("about.project");
        printStream.println(about);
    }

    protected void argumentLengthCheck(final String[] arguments) {
        if(0 == arguments.length){
            final Image2CssHelpFormatter image2CssHelpFormatter = this.objectFactory.instance(BeanType.helpFormatter);
            final SystemWrapper systemWrapper = this.objectFactory.instance(BeanType.systemWrapper);

            image2CssHelpFormatter.showHelp();
            systemWrapper.exit();
        }
    }

}
