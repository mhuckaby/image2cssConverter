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

import com.rf1m.image2css.cli.CommandLineArgument
import com.rf1m.image2css.domain.CssClass
import com.rf1m.image2css.exception.Image2CssException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

import static java.lang.String.format

@Component("htmlOutput")
class HtmlFileOutput extends BaseFileOutput implements Output {
    @Value('${template.html.css.entry}')
    String htmlCssEntryTemplate

    @Value('${template.html.index}')
    String htmlIndexTemplate

    @Override
    public void out(final CommandLineArgument commandLineArgument, final List<CssClass> cssClasses) throws Image2CssException {
        StringBuffer stringBuffer = new StringBuffer()

        for(CssClass cssClass : cssClasses){
            stringBuffer.append(format(htmlCssEntryTemplate, cssClass.name))
        }

        String html = format(htmlIndexTemplate, commandLineArgument.cssFile, stringBuffer.toString())
        write(commandLineArgument.htmlFile, html)
    }

}