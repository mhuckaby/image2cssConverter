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
package com.rf1m.image2css.io

import com.rf1m.image2css.domain.SupportedImageType
import org.apache.commons.io.FilenameUtils

import static com.rf1m.image2css.domain.SupportedImageType.valueOf
import static java.util.Collections.unmodifiableSet

/**
 * Filename filter used to limit which image files are converted.
 *
 */
class ConversionFilenameFilter implements FilenameFilter{
    protected final Set<SupportedImageType> supportedTypes

	ConversionFilenameFilter(final Set<SupportedImageType> supportedTypes){
		this.supportedTypes = unmodifiableSet(supportedTypes)
	}

	@Override
	boolean accept(final File fileDir, final String filename) {
        final String extension = FilenameUtils.getExtension(filename)
        return extension ? this.isSupported(extension) : false
	}

    protected boolean isSupported(final String extension) {
        try {
            return supportedTypes.contains(valueOf(extension))
        }catch(final IllegalArgumentException e){
            return false
        }
    }

}