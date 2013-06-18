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

import com.rf1m.image2css.cmn.exception.Image2CssException;
import org.apache.commons.cli.ParseException;

import java.io.PrintStream;
import java.util.ResourceBundle;

public class ExceptionHandler {
    protected final Image2CssHelpFormatter image2CssHelpFormatter;
    protected final PrintStream printStream;
    protected final ResourceBundle resourceBundle;


    public ExceptionHandler(final Image2CssHelpFormatter image2CssHelpFormatter,
                            final PrintStream printStream, final ResourceBundle resourceBundle) {

        this.image2CssHelpFormatter = image2CssHelpFormatter;
        this.printStream = printStream;
        this.resourceBundle = resourceBundle;
    }

    protected void handleException(final Exception e) {
        final String issueUrl = this.getString("issue.url");
        final String messageTemplate = this.getString("message.abnormal.exit");
        final String formattedMessage = this.format(messageTemplate, e.getMessage(), issueUrl);

        printStream.println(formattedMessage);
        e.printStackTrace();
    }

    protected void handleImage2CssException(final Image2CssException image2CssException) {
        final String exceptionFormat = this.getString("format.exception");
        final String formattedExceptionMessage = this.format(exceptionFormat, image2CssException.getMessage());

        printStream.println(formattedExceptionMessage);
    }

    protected void handleParseException(final ParseException parseException) {
        final String exceptionFormat = this.getString("format.exception");
        final String formattedExceptionMessage = this.format(exceptionFormat, parseException.getMessage());

        printStream.println(formattedExceptionMessage);
        image2CssHelpFormatter.showHelp();
    }

    protected String format(final String template, final String ... param) {
        return String.format(template, param);
    }

    protected String getString(final String key) {
        return this.resourceBundle.getString(key);
    }

}
