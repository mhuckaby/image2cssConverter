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
import com.rf1m.image2css.cmn.exception.Errors;
import com.rf1m.image2css.cmn.exception.Image2CssException;
import com.rf1m.image2css.cmn.ioc.CommonObjectFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Set;

public class FileUtils {
    protected final CommonObjectFactory commonObjectFactory;
    protected final Set<SupportedImageType> defaultSupportedImageTypes;

    public FileUtils(final CommonObjectFactory commonObjectFactory,
                     final Set<SupportedImageType> defaultSupportedImageTypes) {

        this.commonObjectFactory = commonObjectFactory;
        this.defaultSupportedImageTypes = defaultSupportedImageTypes;
    }

    /**
	 * Return the file-extension, null if there is none.
	 * @param path
	 * @return
	 */
	public String getExtension(final String path){
        final int notFound = -1;
        final int positionsAfterLastIndex = 1;
		final int lastIndex = path.lastIndexOf('.');

        if(notFound == lastIndex || path.length() == lastIndex){
			return "";
		}else{
            final int afterLastPeriod = lastIndex + positionsAfterLastIndex;
			return path.substring(afterLastPeriod).toLowerCase();
		}
	}

	/**
	 * Read the bytes of a file into a byte array and return the array.
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public byte[] getFileBytes(final File file) {
		final FileInputStream fileInputStream = this.commonObjectFactory.newFileInputStream(file);
		final byte[] bytes = this.commonObjectFactory.newByteArray(file.length());

        try {
            fileInputStream.read(bytes);
        }catch(final IOException ioException) {
            throw new Image2CssException(ioException, Errors.errorReadingFile);
        }finally {
            try {
                fileInputStream.close();
            }catch(final Exception exception) {
                throw new Image2CssException(exception, Errors.errorClosingFile);
            }
        }

        return bytes;
	}

    public File[] getImagesForConversion(final File imageFile, final Set<SupportedImageType> supportedTypes) throws Image2CssException {
        if(imageFile.isDirectory()){
            final Set<SupportedImageType> supportedTypesToFilterFor = supportedTypes.isEmpty() ? defaultSupportedImageTypes : supportedTypes;
            final ConversionFilenameFilter filter = this.commonObjectFactory.newConversionFilenameFilter(supportedTypesToFilterFor);

            return imageFile.listFiles(filter);
        }else{
            return new File[] {imageFile};
        }
    }

}