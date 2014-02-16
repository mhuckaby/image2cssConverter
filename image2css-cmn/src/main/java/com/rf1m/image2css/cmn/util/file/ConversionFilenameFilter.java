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
package com.rf1m.image2css.cmn.util.file;

import com.rf1m.image2css.domain.SupportedImageType;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Set;

import static com.rf1m.image2css.domain.SupportedImageType.valueOf;
import static java.util.Collections.unmodifiableSet;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Filename filter used to limit which image files are converted.
 *
 */
public class ConversionFilenameFilter implements FilenameFilter{
    protected final Set<SupportedImageType> supportedTypes;
    protected final FileUtils fileUtils;

	public ConversionFilenameFilter(final FileUtils fileUtils, final Set<SupportedImageType> supportedTypes){
		this.supportedTypes = unmodifiableSet(supportedTypes);
        this.fileUtils = fileUtils;
	}
	
	@Override
	public boolean accept(final File fileDir, final String filename) {
        final String extension = this.fileUtils.getExtension(filename);
        return isNotBlank(extension) ? this.isSupported(extension) : false;
	}

    protected boolean isSupported(final String extension) {
        try{
            final SupportedImageType supportedImageType = valueOf(extension);
            return supportedTypes.contains(supportedImageType);
        }catch(final IllegalArgumentException e){
            return false;
        }
    }

}