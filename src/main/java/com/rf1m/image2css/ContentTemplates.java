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
package com.rf1m.image2css;

public interface ContentTemplates {
    final String ABOUT                  = "Image2CSS 1.1.0, Matthew D. Huckaby, 2012";
    final String REPORT_CSS_TOTAL 	    = "Generated [%1$s] CSS entries";
    final String REPORT_CSS_FILE 	    = "Created CSS file, %1$s";
    final String REPORT_HTML_FILE 	    = "Created HTML file, %1$s";

    public interface TEMPLATE{
        public static final String HTML_INDEX       = "template.html.index";
        public static final String HTML_CSS_ENTRY   = "template.html.css.entry";
        public static final String CSS_CLASS        = "template.css.class.def";
    }
}
