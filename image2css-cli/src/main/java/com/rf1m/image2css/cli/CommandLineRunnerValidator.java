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
import com.rf1m.image2css.cmn.exception.Image2CssValidationException;

import static com.rf1m.image2css.cmn.exception.Errors.*;

public class CommandLineRunnerValidator {

    protected final Image2CssHelpFormatter image2CssHelpFormatter;
    protected final SystemWrapper systemWrapper;

    public CommandLineRunnerValidator(final Image2CssHelpFormatter image2CssHelpFormatter, final SystemWrapper systemWrapper) {
        this.image2CssHelpFormatter = image2CssHelpFormatter;
        this.systemWrapper = systemWrapper;
    }

    protected void argumentLengthCheck(final String[] arguments) {
        if(0 == arguments.length){
            this.image2CssHelpFormatter.showHelp();
            this.systemWrapper.exit();
        }
    }

    public void validateParameters(final Parameters parameters) throws Image2CssException {
        if(null == parameters){
            throw new Image2CssValidationException(parametersObjectCannotBeNull) ;
        }else if(parameters.isLocalResource() && null == parameters.getImageFile()){
            throw new Image2CssValidationException(parametersObjectMustHaveValidImageInputFileOrDir);
        }else if(parameters.isLocalResource() && !parameters.getImageFile().exists()){
            throw new Image2CssValidationException(parametersObjectImageInputFileOrDirNotExists);
        }else if(!parameters.isLocalResource() && null == parameters.getURL()) {
            throw new Image2CssValidationException(parametersUrlCannotBeNull);
        }else if(!parameters.isOutputValid()){
            throw new Image2CssValidationException(parametersObjectOutputInvalid);
        }else if(parameters.isHtmlFileOutputDesired() && !parameters.isCssFileOutputDesired()) {
            throw new Image2CssValidationException(parameterHtmlIndexWithNoCssFile);
        }
    }

}
