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

import com.rf1m.image2css.exception.Image2CssException;
import org.apache.commons.cli.ParseException;

public class ExceptionHandler {
    protected final Image2CssHelpFormatter image2CssHelpFormatter;

    protected final String issueUrl;
    protected final String abnormalExitTemplate;
    protected final String exceptionMessageTemplate;

    public ExceptionHandler(final Image2CssHelpFormatter image2CssHelpFormatter,
                            final String issueUrl,
                            final String abnormalExitTemplate,
                            final String exceptionMessageTemplate) {

        this.image2CssHelpFormatter = image2CssHelpFormatter;
        this.issueUrl = issueUrl;
        this.abnormalExitTemplate = abnormalExitTemplate;
        this.exceptionMessageTemplate = exceptionMessageTemplate;
    }

    protected void handleException(final Exception e) {
        final String formattedMessage = this.format(abnormalExitTemplate, e.getMessage(), issueUrl);
        this.println(formattedMessage);

        e.printStackTrace();
    }

    protected void handleImage2CssException(final Image2CssException image2CssException) {
        final String formattedExceptionMessage = this.format(exceptionMessageTemplate, image2CssException.getMessage());
        this.println(formattedExceptionMessage);
    }

    protected void handleParseException(final ParseException parseException) {
        final String formattedExceptionMessage = this.format(exceptionMessageTemplate, parseException.getMessage());
        this.println(formattedExceptionMessage);

        image2CssHelpFormatter.showHelp();
    }

    protected String format(final String template, final String ... param) {
        return String.format(template, param);
    }

    protected void println(final String out) {
        System.out.println(out);
    }

}
