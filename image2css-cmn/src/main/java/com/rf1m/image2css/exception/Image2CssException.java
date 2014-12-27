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
package com.rf1m.image2css.exception;

import java.util.ResourceBundle;

import static java.lang.String.format;

public class Image2CssException extends RuntimeException {
    protected static final ResourceBundle resourceBundle = ResourceBundle.getBundle("image2css-exception-messages");

    public Image2CssException(final Errors errors) {
        super(resourceBundle.getString(errors.getMessage()));
    }

    public Image2CssException(final Throwable cause, final Errors errors) {
        super(resourceBundle.getString(errors.getMessage()), cause);
    }

    public Image2CssException(final Errors errors, final Object ... parameters) {
        super(format(resourceBundle.getString(errors.getMessage()), parameters));
    }

}