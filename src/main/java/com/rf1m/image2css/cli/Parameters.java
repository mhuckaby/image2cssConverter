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

import java.io.File;
import java.util.Set;

public interface Parameters{

	/**
	 * Directory of image-files or single image file to be converted to a single CSS class file.
	 * @return
	 */
	File getImageFile();
	
	/**
	 * CSS file where the image data will be written as CSS classes.
	 * @return
	 */
	File getCssFile();

	/**
	 * HTML file where the generated CSS classes are demonstrated.
	 * @return
	 */
	File getHtmlFile();

	/**
	 * Image types that will be included in filter when image-file is a directory.
	 * @return
	 */
	Set<SupportedImageTypes> getSupportedTypes();

    /**
     * Valid if css file or console is specified.
     * @return
     */
    boolean isOutputValid();

    /**
     * True, if css file is supplied.
     * @return
     */
    boolean isCssFileOutputDesired();

    /**
     * True, if html file is supplied.
     * @return
     */
    boolean isHtmlFileOutputDesired();

    /**
     * True, if output to console is supplied.
     * @return boolean,
     */
    boolean isOutputToConsoleDesired();

}
