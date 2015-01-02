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
package com.rf1m.image2css.cli

import com.rf1m.image2css.exception.Image2CssException
import org.apache.commons.cli.ParseException

import static java.lang.String.format


class ExceptionHandler {
    protected final Image2CssHelpFormatter image2CssHelpFormatter

    protected final String issueUrl
    protected final String abnormalExitTemplate
    protected final String exceptionMessageTemplate

    protected PrintStream defaultOut = System.out

    public ExceptionHandler(final Image2CssHelpFormatter image2CssHelpFormatter,
                            final String issueUrl,
                            final String abnormalExitTemplate,
                            final String exceptionMessageTemplate) {

        this.image2CssHelpFormatter = image2CssHelpFormatter
        this.issueUrl = issueUrl
        this.abnormalExitTemplate = abnormalExitTemplate
        this.exceptionMessageTemplate = exceptionMessageTemplate
    }

    protected void handleException(final Exception e) {
        defaultOut.println(format(abnormalExitTemplate, e.message, issueUrl))
        e.printStackTrace()
    }

    protected void handleImage2CssException(final Image2CssException image2CssException) {
        defaultOut.println(format(exceptionMessageTemplate, image2CssException.message))
    }

    protected void handleParseException(final ParseException parseException) {
        defaultOut.println(format(exceptionMessageTemplate, parseException.message))
        image2CssHelpFormatter.showHelp()
    }

}
