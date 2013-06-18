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
package com.rf1m.image2css.out;

import com.rf1m.image2css.cli.Parameters;
import com.rf1m.image2css.cmn.domain.CssClass;
import com.rf1m.image2css.cmn.ioc.BeanType;
import com.rf1m.image2css.ioc.CliObjectFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static java.lang.String.format;

public class HTMLOutput extends AbstractOutput {
    protected final CliObjectFactory objectFactory;
    protected final String htmlCssEntryTemplate;
    protected final String htmlIndexTemplate;

    public HTMLOutput(final CliObjectFactory objectFactory,
                      final String htmlCssEntryTemplate,
                      final String htmlIndexTemplate) {

        this.objectFactory = objectFactory;
        this.htmlCssEntryTemplate = htmlCssEntryTemplate;
        this.htmlIndexTemplate = htmlIndexTemplate;
    }

    @Override
    public void out(final Parameters parameters, final List<CssClass> cssClasses) throws IOException {
        if(super.isValidParametersAndClasses(parameters, cssClasses)){
            final FileWriter fileWriter = this.objectFactory.getInstance(BeanType.fileWriter, parameters.getHtmlFile());
            final StringBuffer stringBuffer = this.objectFactory.getInstance(BeanType.stringBuffer);

            for(final CssClass cssClass : cssClasses){
                final String formattedEntry = format(htmlCssEntryTemplate, cssClass.getName());
                stringBuffer.append(formattedEntry);
            }

            final String cssFilename = parameters.getCssFile().getName();
            final String formattedLine = format(htmlIndexTemplate, cssFilename, stringBuffer.toString());

            fileWriter.write(formattedLine);
			fileWriter.close();
		}
    }

}