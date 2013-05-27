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
import java.util.Collections;
import java.util.Set;

public class ImmutableParameters implements Parameters {
	protected final File imageFile;
	protected final File cssFile;
	protected final File htmlFile;
	protected final Set<SupportedImageType> supportedTypes;
	protected boolean outputToScreen;
	
	public ImmutableParameters(
            final File imageFile,
            final File cssFile,
            final File htmlFile,
            final Set<SupportedImageType> supportedTypes,
            final boolean outputToScreen) {

		this.imageFile = imageFile;
		this.cssFile = cssFile;
		this.htmlFile = htmlFile;
		this.supportedTypes = null == supportedTypes ? null : Collections.unmodifiableSet(supportedTypes);
		this.outputToScreen = outputToScreen;
	}
	
	@Override
	public boolean isOutputToConsoleDesired() {
		return outputToScreen;
	}

	@Override
	public File getImageFile() {
		return imageFile;
	}

	@Override
	public File getCssFile() {
		return cssFile;
	}

	@Override
	public File getHtmlFile() {
		return htmlFile;
	}

	@Override
	public Set<SupportedImageType> getSupportedTypes() {
		return supportedTypes;
	}

    @Override
    public boolean isOutputValid() {
        return !(null == this.cssFile && !outputToScreen);
    }

    @Override
    public boolean isCssFileOutputDesired() {
        return null != this.cssFile;
    }

    @Override
    public boolean isHtmlFileOutputDesired() {
        return null != this.htmlFile;
    }

}
