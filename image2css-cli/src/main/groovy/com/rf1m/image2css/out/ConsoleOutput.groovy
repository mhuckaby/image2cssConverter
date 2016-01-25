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
package com.rf1m.image2css.out

import com.rf1m.image2css.cli.Parameters
import com.rf1m.image2css.domain.CssClass
import com.rf1m.image2css.exception.Image2CssException
import com.rf1m.image2css.io.ReportOutput

import static java.lang.String.format

class ConsoleOutput extends AbstractOutput implements ReportOutput{
    protected final String reportCssTotalTemplate
    protected final String reportCssFileTemplate
    protected final String reportHtmlFileTemplate

    protected PrintStream defaultOut = System.out

    public ConsoleOutput(final String reportCssTotalTemplate,
                         final String reportCssFileTemplate,
                         final String reportHtmlFileTemplate) {

        this.reportCssTotalTemplate = reportCssTotalTemplate
        this.reportCssFileTemplate = reportCssFileTemplate
        this.reportHtmlFileTemplate = reportHtmlFileTemplate
    }

    @Override
    public void out(final Parameters parameters, final List<CssClass> cssClasses) throws Image2CssException {
        if(isValidParametersAndClassesWithConsoleOutput(parameters, cssClasses)){
			for(CssClass cssClass : cssClasses){
                defaultOut.println(cssClass.body)
			}
		}
    }

    @Override
    public void generateReportOutput(final Parameters parameters, final List<CssClass> cssClasses){
        if(super.isValidParametersAndClasses(parameters, cssClasses)){
            if(!parameters.outputToConsoleDesired) {
                this.println(format(reportCssTotalTemplate, cssClasses.size()))
            }

            if(null != parameters.getCssFile()){
                defaultOut.println(format(reportCssFileTemplate, parameters.cssFile.name))
            }

            if(null != parameters.getHtmlFile()){
                defaultOut.println(format(reportHtmlFileTemplate, parameters.htmlFile.name))
            }
        }
	}

    protected boolean isValidParametersAndClassesWithConsoleOutput(final Parameters parameters,
                                                                   final List<CssClass> cssClasses) {

        return super.isValidParametersAndClasses(parameters, cssClasses) && parameters.outputToConsoleDesired
    }

}
