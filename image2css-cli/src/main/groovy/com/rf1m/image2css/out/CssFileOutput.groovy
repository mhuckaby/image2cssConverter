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

class CssFileOutput extends AbstractOutput{
    protected static final String NL = "\n"

    @Override
    void out(final Parameters parameters, final List<CssClass> cssClasses) throws Image2CssException {
        if(isValidParametersAndClasses(parameters, cssClasses)){
            StringBuffer stringBuffer = new StringBuffer()
            for(CssClass cssClass : cssClasses){
                stringBuffer.append(cssClass.body).append(NL)
			}
            try {
                FileWriter fileWriter = new FileWriter(parameters.cssFile)
                fileWriter.write(stringBuffer.toString())
                fileWriter.close()
            }catch(IOException e) {
                throw new Image2CssException(e, Error.errorWritingFile)
            }

		}
    }

}