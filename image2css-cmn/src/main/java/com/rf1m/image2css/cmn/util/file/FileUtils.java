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

import com.rf1m.image2css.cmn.domain.SupportedImageType;

import java.util.Set;

public class FileUtils {
    protected static final char period = '.';
    protected static final int notFound = -1;
    protected static final int positionsAfterLastIndex = 1;

    protected static final String empty = "";

    protected final Set<SupportedImageType> defaultSupportedImageTypes;

    public FileUtils(final Set<SupportedImageType> defaultSupportedImageTypes) {
        this.defaultSupportedImageTypes = defaultSupportedImageTypes;
    }

    /**
	 * Return the file-extension, empty if there is none.
	 * @param path
	 * @return
	 */
	public String getExtension(final String path){
		final int lastIndex = path.lastIndexOf(period);

        if(notFound == lastIndex){
			return empty;
		}else{
            final int afterLastPeriod = (lastIndex + positionsAfterLastIndex);
			return path.substring(afterLastPeriod).toLowerCase();
		}
	}

}